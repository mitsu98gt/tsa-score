package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.*;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@RequestScoped
@ManagedBean(name="scoresheetBean")
public class GSSFIndoorScoreSheetBean implements Serializable {

	private GSSFIndoorScoreSheet scoreSheet;
    private StreamedContent file;

    @ManagedProperty("#{competitorDataSource}")
    private CompetitorDataSource competitorDataSource;

    @ManagedProperty("#{firearmDataSource}")
    private FirearmDataSource firearmDataSource;

    @ManagedProperty("#{competitionResultsDAO}")
    private CompetitionResultsDAO competitionResultsDAO;

    private List<Firearm> firearmList;

    private String[] selectedDivisions;

    private Competition competition;

    public GSSFIndoorScoreSheetBean(){}

    @PostConstruct
    public void init() {
        scoreSheet = new GSSFIndoorScoreSheet();
        firearmList = firearmDataSource.getFirearms();
    }

    public void onCompetitionChange() {
        scoreSheet.setDate(competition.getDate());
        competitorDataSource.getCompetitorsForScoreSheetByCompetitionId(Integer.valueOf(competition.getId()));
    }

    public void doScore() {
        parseSelectedDivisions();
        if (doValidation()){
            calculateTargetTotals();
            calculateSumRow();
            calculateTotalRow();
        }
    }

    public boolean doValidation() {

        if (!validateDivisions()) {
            return false;
        }

        if (!validateStockTotals()) {
            return false;
        }

        if (!validatePocketTotals()) {
            return false;
        }

        return true;
    }

    public boolean validateDivisions() {

        if (!isDivisionSelected()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Please select a division!"));
            return false;
        }

        if (!isOnlyStock()) {
            return false;
        }

        if (!isOnlyUnlimited()) {
            return false;
        }

        if (!isOnlyPocket()) {
            return false;
        }

        if (!validateSeniorJunior()) {
            return false;
        }

        if (!validateWoman()) {
            return false;
        }

        if (!validateSenior()) {
            return false;
        }

        if (!validateJunior()) {
            return false;
        }

        return true;
    }

    public boolean validateWoman() {
        if (scoreSheet.getDivsion().isWoman()) {
            if(scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Woman division applies to only Stock!"));
                return false;
            }
        }
        return true;
    }

    public boolean validateSenior() {
        if (scoreSheet.getDivsion().isSenior()) {
            if(scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Senior division applies to only Stock!"));
                return false;
            }
        }
        return true;
    }

    public boolean validateJunior() {
        if (scoreSheet.getDivsion().isJunior()) {
            if(scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Junior division applies to only Stock!"));
                return false;
            }
        }
        return true;
    }

    public boolean validateSeniorJunior() {
        if (scoreSheet.getDivsion().isSenior() && scoreSheet.getDivsion().isJunior()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Senior and Junior divisions can't be both selected!"));
            return false;
        }
        return true;
    }

    public boolean isDivisionSelected() {
        return (scoreSheet.getDivsion().isStock() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) ? true : false;
    }

    public boolean isOnlyStock() {
        if (scoreSheet.getDivsion().isStock()) {
            if (scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Stock, Unlimited, or Pocket divisions can be selected!"));
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyUnlimited() {
        if (scoreSheet.getDivsion().isUnlimited()) {
            if (scoreSheet.getDivsion().isStock() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Stock, Unlimited, or Pocket divisions can be selected!"));
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyPocket() {
        if (scoreSheet.getDivsion().isPocket()) {
            if (scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isStock() || scoreSheet.getDivsion().isWoman() || scoreSheet.getDivsion().isSenior() || scoreSheet.getDivsion().isJunior()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only Pocket division can be selected!"));
                return false;
            }
            if (!scoreSheet.getFirearm().getModel().equals("G42") && !scoreSheet.getFirearm().getModel().equals("G43")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Firearm model not applicable for Pocket division!"));
                return false;
            }
        }
        return true;
    }

    public boolean validateStockTotals() {

        boolean pass = true;

        if (!scoreSheet.getDivsion().isPocket()) {
            if (calculateTargetOneTotals() != 20) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 1 totals are incorrect!"));
                pass = false;
            }
            if (calculateTargetTwoTotals() != 30) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 2 totals are incorrect!"));
                pass = false;
            }
        }
        return pass;
    }

    public boolean validatePocketTotals() {

        boolean pass = true;

        if (scoreSheet.getDivsion().isPocket()) {
            if (calculateTargetOneTotals() != 10) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 1 totals are incorrect!"));
                pass = false;
            }
            if (calculateTargetTwoTotals() != 15) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 2 totals are incorrect!"));
                pass = false;
            }
        }
        return pass;
    }

    public void saveScoreSheetPDF() throws Exception {
        doScore();
        GSSFIndoorScoreSheetPDF pdf = new GSSFIndoorScoreSheetPDF();
        pdf.savePDF(scoreSheet);
    }

    public StreamedContent downloadScoreSheetPDF() throws Exception {
        doScore();
        GSSFIndoorScoreSheetPDF pdf = new GSSFIndoorScoreSheetPDF();
        return new DefaultStreamedContent(pdf.downloadPDF(scoreSheet), "application/pdf", scoreSheet.getCompetitor().getFirstName()+scoreSheet.getCompetitor().getLastName()+"_Scoresheet_"+competition.getDate().toString()+".pdf");
    }

    public void saveToDatabase() {

        try {
            competitionResultsDAO.addCompetitionResults(buildCompetitionResults());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Scores saved successfully!"));
        } catch (Exception ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Scores did not save!"));
        }

    }

    public CompetitionResults buildCompetitionResults() throws Exception {

        doScore();

        CompetitionResults competitionResults = new CompetitionResults();
        CompetitionDetails competitionDetails = new CompetitionDetails();
        CompetitionCode competitionCode = new CompetitionCode();

        competitionCode.setCode("1");
        competitionDetails.setCompetitionCode(competitionCode);

        competitionDetails.setCompetitionDetailsId(competition.getId());
        //competitionDetails.setDate(DateUtils.getDateFromString("2018-01-06"));
        competitionDetails.setDate(competition.getDate());
        competitionResults.setCompetitionDetails(competitionDetails);

        CompetitionCompetitors competitionCompetitors = new CompetitionCompetitors();
        competitionCompetitors.setCompetitorId(scoreSheet.getCompetitor().getCompetitorId());
        competitionCompetitors.setFirearmId(scoreSheet.getFirearm().getId());
        competitionResults.setCompetitionCompetitors(competitionCompetitors);

        parseSelectedDivisions();
        scoreSheet.setTotalX(calculateTotalX());

        competitionResults.setGssfIndoorScoreSheet(scoreSheet);

        return competitionResults;
    }

    public void calculateTargetTotals() {
        scoreSheet.getTargetOne().setTotal(calculateTargetOneTotals());
        scoreSheet.getTargetTwo().setTotal(calculateTargetTwoTotals());
    }

    public void calculateSumRow() {
        scoreSheet.getSumRow().setX(scoreSheet.getTargetOne().getX() + scoreSheet.getTargetTwo().getX());
        scoreSheet.getSumRow().setTen(scoreSheet.getTargetOne().getTen() + scoreSheet.getTargetTwo().getTen());
        scoreSheet.getSumRow().setEight(scoreSheet.getTargetOne().getEight() + scoreSheet.getTargetTwo().getEight());
        scoreSheet.getSumRow().setFive(scoreSheet.getTargetOne().getFive() + scoreSheet.getTargetTwo().getFive());
        scoreSheet.getSumRow().setMisses(scoreSheet.getTargetOne().getMisses() + scoreSheet.getTargetTwo().getMisses());
        scoreSheet.getSumRow().setTotal(scoreSheet.getTargetOne().getTotal() + scoreSheet.getTargetTwo().getTotal());
    }

    public void calculateTotalRow() {
        scoreSheet.getTotalRow().setX(scoreSheet.getSumRow().getX() * 10);
        scoreSheet.getTotalRow().setTen(scoreSheet.getSumRow().getTen() * 10);
        scoreSheet.getTotalRow().setEight(scoreSheet.getSumRow().getEight() * 8);
        scoreSheet.getTotalRow().setFive(scoreSheet.getSumRow().getFive() * 5);
        scoreSheet.setFinalScore(calculateTotalScore() - scoreSheet.getPenalty());
    }

    public int calculateTotalX() {
        return scoreSheet.getTargetOne().getX() + scoreSheet.getTargetTwo().getX();
    }

    public int calculateTargetOneTotals() {
        return (scoreSheet.getTargetOne().getX() + scoreSheet.getTargetOne().getTen() + scoreSheet.getTargetOne().getEight() + scoreSheet.getTargetOne().getFive() + scoreSheet.getTargetOne().getMisses());
    }

    public int calculateTargetTwoTotals() {
        return (scoreSheet.getTargetTwo().getX() + scoreSheet.getTargetTwo().getTen() + scoreSheet.getTargetTwo().getEight() + scoreSheet.getTargetTwo().getFive() + scoreSheet.getTargetTwo().getMisses());
    }

    public int calculateTotalScore() {
        return (scoreSheet.getTotalRow().getX() + scoreSheet.getTotalRow().getTen() + scoreSheet.getTotalRow().getEight() + scoreSheet.getTotalRow().getFive());
    }

    public List<Competitor> complete(String query){
        // Assumed Datasource
        return competitorDataSource.queryByName(query);
    }

    public void parseSelectedDivisions(){

        List<String> divisionList = Arrays.asList(getSelectedDivisions());

        scoreSheet.getDivsion().setUnlimited(divisionList.contains(Constants.GSSF_UNLIMITED) ? true : false);
        scoreSheet.getDivsion().setStock(divisionList.contains(Constants.GSSF_STOCK) ? true : false);
        scoreSheet.getDivsion().setPocket(divisionList.contains(Constants.GSSF_POCKET) ? true : false);
        scoreSheet.getDivsion().setWoman(divisionList.contains(Constants.GSSF_WOMAN) ? true : false);
        scoreSheet.getDivsion().setSenior(divisionList.contains(Constants.GSSF_SENIOR) ? true : false);
        scoreSheet.getDivsion().setJunior(divisionList.contains(Constants.GSSF_JUNIOR) ? true : false);

    }

    public CompetitorDataSource getCompetitorDataSource() {
        return competitorDataSource;
    }

    public void setCompetitorDataSource(CompetitorDataSource competitorDataSource) {
        this.competitorDataSource = competitorDataSource;
    }

    public FirearmDataSource getFirearmDataSource() {
        return firearmDataSource;
    }

    public void setFirearmDataSource(FirearmDataSource firearmDataSource) {
        this.firearmDataSource = firearmDataSource;
    }

    public void setCompetitionResultsDAO(CompetitionResultsDAO competitionResultsDAO) {
        this.competitionResultsDAO = competitionResultsDAO;
    }

    public GSSFIndoorScoreSheet getScoreSheet() {
        return scoreSheet;
    }

    public void setScoreSheet(GSSFIndoorScoreSheet scoreSheet) {
        this.scoreSheet = scoreSheet;
    }

    public String[] getSelectedDivisions() {
        return selectedDivisions;
    }

    public void setSelectedDivisions(String[] selectedDivisions) {
        this.selectedDivisions = selectedDivisions;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<Firearm> getFirearmList() {
        return firearmList;
    }

    public void setFirearmList(List<Firearm> firearmList) {
        this.firearmList = firearmList;
    }

    public StreamedContent getFile() throws Exception {
        return (null == file) ? downloadScoreSheetPDF() : file;
    }
}
