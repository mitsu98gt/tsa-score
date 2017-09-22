package com.envisageconsulting.primefaces.scoredaddy.domain;

import java.util.Date;
import java.util.List;

public class CompetitionDetails {

    private String competitionDetailsId;
    private CompetitionCode competitionCode;
    private Date date;
    private String course;

    public String getCompetitionDetailsId() {
        return competitionDetailsId;
    }

    public void setCompetitionDetailsId(String competitionDetailsId) {
        this.competitionDetailsId = competitionDetailsId;
    }

    public CompetitionCode getCompetitionCode() {
        return competitionCode;
    }

    public void setCompetitionCode(CompetitionCode competitionCode) {
        this.competitionCode = competitionCode;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}
