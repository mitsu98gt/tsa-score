package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.CompetitorDataSource;
import com.envisageconsulting.primefaces.scoredaddy.FirearmDataSource;
import com.envisageconsulting.primefaces.scoredaddy.GSSFIndoorScoreSheetPDF;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.bean.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@RequestScoped
@ManagedBean(name="scoresheetBean")
public class GSSFIndoorScoreSheetBean implements Serializable {

    private static final String GSSF_UNLIMITED = "GSSF_UNLIMITED";
    private static final String GSSF_STOCK = "GSSF_STOCK";
    private static final String GSSF_POCKET = "GSSF_POCKET";
    private static final String GSSF_WOMAN = "GSSF_WOMAN";
    private static final String GSSF_SENIOR = "GSSF_SENIOR";
    private static final String GSSF_JUNIOR = "GSSF_JUNIOR";

	private GSSFIndoorScoreSheet scoreSheet;
    private StreamedContent file;

    @ManagedProperty("#{competitorDataSource}")
    private CompetitorDataSource competitorDataSource;

    @ManagedProperty("#{firearmDataSource}")
    private FirearmDataSource firearmDataSource;

    private List<Firearm> firearmList;

    private String[] selectedDivisions;

    public GSSFIndoorScoreSheetBean(){}

    @PostConstruct
    public void init() {
        scoreSheet = new GSSFIndoorScoreSheet();
        competitorDataSource.init();
        firearmList = firearmDataSource.getFirearms();
    }

    public void doScore() {
        parseSelectedDivisions();
        calculateTargetTotals();
        calculateSumRow();
        calculateTotalRow();
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

    public Integer calculateTargetOneTotals() {
        return (scoreSheet.getTargetOne().getX() + scoreSheet.getTargetOne().getTen() + scoreSheet.getTargetOne().getEight() + scoreSheet.getTargetOne().getFive() + scoreSheet.getTargetOne().getMisses());
    }

    public Integer calculateTargetTwoTotals() {
        return (scoreSheet.getTargetTwo().getX() + scoreSheet.getTargetTwo().getTen() + scoreSheet.getTargetTwo().getEight() + scoreSheet.getTargetTwo().getFive() + scoreSheet.getTargetTwo().getMisses());
    }

    public Integer calculateTotalScore() {
        return (scoreSheet.getTotalRow().getX() + scoreSheet.getTotalRow().getTen() + scoreSheet.getTotalRow().getEight() + scoreSheet.getTotalRow().getFive());
    }

    public List<Competitor> complete(String query){
        // Assumed Datasource
        return competitorDataSource.queryByName(query);
    }

    public void parseSelectedDivisions(){

        List<String> divisionList = Arrays.asList(getSelectedDivisions());

        scoreSheet.getDivsion().setUnlimited(divisionList.contains(GSSF_UNLIMITED) ? true : false);
        scoreSheet.getDivsion().setStock(divisionList.contains(GSSF_STOCK) ? true : false);
        scoreSheet.getDivsion().setPocket(divisionList.contains(GSSF_POCKET) ? true : false);
        scoreSheet.getDivsion().setWoman(divisionList.contains(GSSF_WOMAN) ? true : false);
        scoreSheet.getDivsion().setSenior(divisionList.contains(GSSF_SENIOR) ? true : false);
        scoreSheet.getDivsion().setJunior(divisionList.contains(GSSF_JUNIOR) ? true : false);

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
