package com.envisageconsulting.primefaces.scoredaddy.domain;

import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;

public class CompetitionResults {

    private CompetitionDetails competitionDetails;
    private CompetitionCompetitors competitionCompetitors;
    private GSSFIndoorScoreSheet gssfIndoorScoreSheet;

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
