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

    public GSSFIndoorScoreSheetBean(){}

    @PostConstruct
    public void init() {
        scoreSheet = new GSSFIndoorScoreSheet();
        competitorDataSource.getCompetitorsForScoreSheetByCompetitionId(2); //TODO Do Not Hard Code
        firearmList = firearmDataSource.getFirearms();
    }

    public void doScore() {
        if (validate()){
            parseSelectedDivisions();
            calculateTargetTotals();
            calculateSumRow();
            calculateTotalRow();
        }
    }

    public boolean validate() {

        boolean pass = true;
        if (calculateTargetOneTotals() != 20) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 1 totals are incorrect!"));
            pass = false;
        }
        if (calculateTargetTwoTotals() != 30) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 2 totals are incorrect!"));
            pass = false;
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
        return new DefaultStreamedContent(pdf.downloadPDF(scoreSheet), "application/pdf", "scoresheet.pdf");
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

        competitionDetails.setCompetitionDetailsId("1");
        competitionDetails.setDate(DateUtils.getDateFromString("2018-01-06"));
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
