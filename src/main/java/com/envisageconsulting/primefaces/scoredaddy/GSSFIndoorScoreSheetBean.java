package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.GSSFIndoorScoreSheet;
import org.primefaces.context.RequestContext;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SessionScoped
@ManagedBean (name = "scoresheetBean")
public class GSSFIndoorScoreSheetBean implements Serializable {

    private static final String GSSF_UNLIMITED = "GSSF_UNLIMITED";
    private static final String GSSF_STOCK = "GSSF_STOCK";
    private static final String GSSF_POCKET = "GSSF_POCKET";
    private static final String GSSF_WOMAN = "GSSF_WOMAN";
    private static final String GSSF_SENIOR = "GSSF_SENIOR";
    private static final String GSSF_JUNIOR = "GSSF_JUNIOR";

	private GSSFIndoorScoreSheet scoreSheet;

    @ManagedProperty("#{competitorDataSource}")
    private CompetitorDataSource ds;

    private String[] selectedDivisions;

    public GSSFIndoorScoreSheetBean(){}

    @PostConstruct
    public void init() {
        scoreSheet = new GSSFIndoorScoreSheet();
    }

    public void doScore() {
        parseSelectedDivisions();
        calculateTargetTotals();
        calculateSumRow();
        calculateTotalRow();
    }

    public void createScoreSheetPDF() throws Exception {
        doScore();
        GSSFIndoorScoreSheetPDF pdf = new GSSFIndoorScoreSheetPDF();
        pdf.createPDF(scoreSheet);
    }

    public void calculateTargetTotals() {
        scoreSheet.setTargetOneTotal(calculateTargetOneTotals());
        scoreSheet.setTargetTwoTotal(calculateTargetTwoTotals());
    }

    public void calculateSumRow() {
        scoreSheet.setSumX(scoreSheet.getTargetOneX() + scoreSheet.getTargetTwoX());
        scoreSheet.setSumTen(scoreSheet.getTargetOneTen() + scoreSheet.getTargetTwoTen());
        scoreSheet.setSumEight(scoreSheet.getTargetOneEight() + scoreSheet.getTargetTwoEight());
        scoreSheet.setSumFive(scoreSheet.getTargetOneFive() + scoreSheet.getTargetTwoFive());
        scoreSheet.setSumMisses(scoreSheet.getTargetOneMisses() + scoreSheet.getTargetTwoMisses());
        scoreSheet.setSumTotal(scoreSheet.getTargetOneTotal() + scoreSheet.getTargetTwoTotal());
    }

    public void calculateTotalRow() {
        scoreSheet.setTotalX(scoreSheet.getSumX() * 10);
        scoreSheet.setTotalTen(scoreSheet.getSumTen() * 10);
        scoreSheet.setTotalEight(scoreSheet.getSumEight() * 8);
        scoreSheet.setTotalFive(scoreSheet.getSumFive() * 5);
        scoreSheet.setFinalScore(calculateTotalScore() - scoreSheet.getPenalty());
    }

    public Integer calculateTargetOneTotals() {
        return (scoreSheet.getTargetOneX() + scoreSheet.getTargetOneTen() + scoreSheet.getTargetOneEight() + scoreSheet.getTargetOneFive() + scoreSheet.getTargetOneMisses());
    }

    public Integer calculateTargetTwoTotals() {
        return (scoreSheet.getTargetTwoX() + scoreSheet.getTargetTwoTen() + scoreSheet.getTargetTwoEight() + scoreSheet.getTargetTwoFive() + scoreSheet.getTargetTwoMisses());
    }

    public Integer calculateTotalScore() {
        return (scoreSheet.getTotalX() + scoreSheet.getTotalTen() + scoreSheet.getTotalEight() + scoreSheet.getTotalFive());
    }

    public List<Competitor> complete(String query){
        // Assumed Datasource
        return ds.queryByName(query);
    }

    public void parseSelectedDivisions(){

        List<String> divisionList = Arrays.asList(getSelectedDivisions());

        scoreSheet.setCheckUnlimited(divisionList.contains(GSSF_UNLIMITED) ? true : false);
        scoreSheet.setCheckStock(divisionList.contains(GSSF_STOCK) ? true : false);
        scoreSheet.setCheckPocket(divisionList.contains(GSSF_POCKET) ? true : false);
        scoreSheet.setCheckWoman(divisionList.contains(GSSF_WOMAN) ? true : false);
        scoreSheet.setCheckSenior(divisionList.contains(GSSF_SENIOR) ? true : false);
        scoreSheet.setCheckJunior(divisionList.contains(GSSF_JUNIOR) ? true : false);

    }

    public CompetitorDataSource getDs() {
        return ds;
    }

    public void setDs(CompetitorDataSource ds) {
        this.ds = ds;
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

}
