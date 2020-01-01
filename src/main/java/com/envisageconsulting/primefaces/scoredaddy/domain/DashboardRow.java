package com.envisageconsulting.primefaces.scoredaddy.domain;

public class DashboardRow {

    private String tournamentName;
    private String competitionName;
    private String competitionDate;
    private String competitionStatusDescription;

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public String getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(String competitionDate) {
        this.competitionDate = competitionDate;
    }

    public String getCompetitionStatusDescription() {
        return competitionStatusDescription;
    }

    public void setCompetitionStatusDescription(String competitionStatusDescription) {
        this.competitionStatusDescription = competitionStatusDescription;
    }
}
