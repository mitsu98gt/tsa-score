package com.envisageconsulting.primefaces.scoredaddy.domain;

import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;

public class CompetitionResults {

    private int rank;
    private String classification;
    private Competition competition;
    private Account account;
    private CompetitionDetails competitionDetails;
    private CompetitionCompetitors competitionCompetitors;
    private GSSFIndoorScoreSheet gssfIndoorScoreSheet;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
