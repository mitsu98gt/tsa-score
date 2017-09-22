package com.envisageconsulting.primefaces.scoredaddy.domain;

public class CompetitionResults {

    private String competitionResultsId;
    private CompetitionDetails competitionDetails;
    private CompetitionCompetitors competitionCompetitors;
    private GSSFIndoorScoreSheet gssfIndoorScoreSheet;

    public String getCompetitionResultsId() {
        return competitionResultsId;
    }

    public void setCompetitionResultsId(String competitionResultsId) {
        this.competitionResultsId = competitionResultsId;
    }

    public CompetitionDetails getCompetitionDetails() {
        return competitionDetails;
    }

    public void setCompetitionDetails(CompetitionDetails competitionDetails) {
        this.competitionDetails = competitionDetails;
    }

    public CompetitionCompetitors getCompetitionCompetitors() {
        return competitionCompetitors;
    }

    public void setCompetitionCompetitors(CompetitionCompetitors competitionCompetitors) {
        this.competitionCompetitors = competitionCompetitors;
    }

    public GSSFIndoorScoreSheet getGssfIndoorScoreSheet() {
        return gssfIndoorScoreSheet;
    }

    public void setGssfIndoorScoreSheet(GSSFIndoorScoreSheet gssfIndoorScoreSheet) {
        this.gssfIndoorScoreSheet = gssfIndoorScoreSheet;
    }
}
