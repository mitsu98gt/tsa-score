package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.*;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@RequestScoped
@ManagedBean(name="bullseyeBean")
public class BullseyeScoreSheetBean implements Serializable {

	private GSSFIndoorScoreSheet scoreSheet;
    private StreamedContent file;

    @ManagedProperty("#{competitorDataSource}")
    private CompetitorDataSource competitorDataSource;

    @ManagedProperty("#{firearmDataSource}")
    private FirearmDataSource firearmDataSource;

    @ManagedProperty("#{competitionResultsDAO}")
    private CompetitionResultsDAO competitionResultsDAO;

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO competitionDAO;

    private List<Firearm> firearmList;

    private String[] selectedDivisions;

    private Competition competition;

    private List<Competition> allCompetitions;

    public BullseyeScoreSheetBean(){}

    @PostConstruct
    public void init() {
        scoreSheet = new GSSFIndoorScoreSheet();
        try {
            allCompetitions = competitionDAO.getBullseyeCompetitionsByAccountIdAndStatus(SessionUtils.getAccountId(), "I");
            firearmList = getListOfFirearms();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            calculatePenalty();
        }
    }

    public List<Firearm> getListOfFirearms() {
        return firearmDataSource.getAllFirearmsForScoreSheet();
    }

    public boolean doValidation() {

        if (!validateDivisions()) {
            return false;
        }

        if (!validateStockTotals()) {
            return false;
        }

        if (!validatRevolverTotals()) {
            return false;
        }

        return true;
    }

    public boolean validateDivisions() {

        if (!isDivisionSelected()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Please select a division!"));
            return false;
        }

        if (!isOnlyLimited()) {
            return false;
        }

        if (!isOnlyUnlimited()) {
            return false;
        }

        if (!isOnlyRevolver()) {
            return false;
        }

        if (!isOnlyRimfire()) {
            return false;
        }

        if (!validateSeniorJunior()) {
            return false;
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
        return (scoreSheet.getDivsion().isLimited() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isRevolver() || scoreSheet.getDivsion().isRimfire()) ? true : false;
    }

    public boolean isOnlyLimited() {
        if (scoreSheet.getDivsion().isLimited()) {
            if (scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isRevolver() || scoreSheet.getDivsion().isRimfire()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Limited, Unlimited, Revolver, or Rimfire divisions can be selected!"));
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyUnlimited() {
        if (scoreSheet.getDivsion().isUnlimited()) {
            if (scoreSheet.getDivsion().isLimited() || scoreSheet.getDivsion().isRevolver() || scoreSheet.getDivsion().isRimfire()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Limited, Unlimited, Revolver, or Rimfire divisions can be selected!"));
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyRevolver() {
        if (scoreSheet.getDivsion().isRevolver()) {
            if (scoreSheet.getDivsion().isLimited() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isRimfire()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Limited, Unlimited, Revolver, or Rimfire divisions can be selected!"));
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyRimfire() {
        if (scoreSheet.getDivsion().isRimfire()) {
            if (scoreSheet.getDivsion().isLimited() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isRevolver()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Limited, Unlimited, Revolver, or Rimfire divisions can be selected!"));
                return false;
            }
        }
        return true;
    }

    public boolean validateStockTotals() {

        boolean pass = true;

        if (!scoreSheet.getDivsion().isRevolver()) {
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

    public boolean validatRevolverTotals() {

        boolean pass = true;

        if (scoreSheet.getDivsion().isRevolver()) {
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
            competitionResultsDAO.addCompetitionResults(buildCompetitionResults(), 0);
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

        competitionCode.setCompetitionCodeId("2");
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
        scoreSheet.setFinalScore(calculateTotalScore());
    }

    public void calculatePenalty() {
        scoreSheet.setPenalty(scoreSheet.getTargetOne().getMisses() + scoreSheet.getTargetTwo().getMisses());
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

        scoreSheet.getDivsion().setUnlimited(divisionList.contains(Constants.BULLSEYE_UNLIMITED) ? true : false);
        scoreSheet.getDivsion().setLimited(divisionList.contains(Constants.BULLSEYE_LIMITED) ? true : false);
        scoreSheet.getDivsion().setRevolver(divisionList.contains(Constants.BULLSEYE_REVOLVER) ? true : false);
        scoreSheet.getDivsion().setRimfire(divisionList.contains(Constants.BULLSEYE_RIMFIRE) ? true : false);
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

    public void setCompetitionDAO(CompetitionDAO competitionDAO) {
        this.competitionDAO = competitionDAO;
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

    public List<Competition> getAllCompetitions() {
        return allCompetitions;
    }

    public void setAllCompetitions(List<Competition> allCompetitions) {
        this.allCompetitions = allCompetitions;
    }

    public StreamedContent getFile() throws Exception {
        return (null == file) ? downloadScoreSheetPDF() : file;
    }

}
