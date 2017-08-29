package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.GSSFIndoorScoreSheet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import java.util.Date;
import java.util.List;

@ViewScoped
@ManagedBean (name = "scoresheetBean")
public class GSSFIndoorScoreSheetBean {

	private GSSFIndoorScoreSheet scoreSheet;

    @ManagedProperty("#{competitorDataSource}")
    private CompetitorDataSource ds;

    private Competitor competitor;
    private String[] selectedDivisions;
    private Date date;
    private String fullName;

    @PostConstruct
    public void init() {
        scoreSheet = new GSSFIndoorScoreSheet();
    }

    public void doScore() {
        calculateTargetTotals();
        calculateSumRow();
        calculateTotalRow();
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
        scoreSheet.setTotalEight(scoreSheet.getSumEight() * 10);
        scoreSheet.setTotalFive(scoreSheet.getSumFive() * 10);
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

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
