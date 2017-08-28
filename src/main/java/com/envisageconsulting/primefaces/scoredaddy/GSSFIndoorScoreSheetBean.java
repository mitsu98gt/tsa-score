package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.GSSFIndoorScoreSheet;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.Date;
import java.util.List;

@SessionScoped
@ManagedBean (name = "scoresheetBean")
public class GSSFIndoorScoreSheetBean {

	private GSSFIndoorScoreSheet scoreSheet;

    @ManagedProperty("#{competitorDataSource}")
    private CompetitorDataSource ds;

    private Competitor competitor;
    private String[] selectedDivisions;
    private Date date;

    @PostConstruct
    public void init() {
        scoreSheet = new GSSFIndoorScoreSheet();
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
}
