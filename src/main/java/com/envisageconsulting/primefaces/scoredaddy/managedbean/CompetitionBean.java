package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionCode;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ManagedBean(name="competitionBean")
@SessionScoped
public class CompetitionBean implements Serializable {

    Competition competition = new Competition();
    CompetitionDetails competitionDetails =  new CompetitionDetails();
    List<CompetitionCode> competitionCodeList = new ArrayList<CompetitionCode>();

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO dao;

    @PostConstruct
    public void init() {
        getCompetitionDetails().setCompetitionCode(new CompetitionCode());
        setCompetitionCodeList(dao.getAllCompetitionCodes());

    }

    public void addCompetition() {
        competition.setAccountId("1"); //TODO Testing only, Remove this at some point.
        dao.addCompetition(getCompetition());
    }

    public void addCompetitionDetails() {
        competitionDetails.setCompetitionDetailsId("1");
        competitionDetails.setCourse("A");
        competitionDetails.setDate(new Date());
        dao.addCompetitionDetails(getCompetitionDetails());
    }

    public void setDao(CompetitionDAO dao) {
        this.dao = dao;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public CompetitionDetails getCompetitionDetails() {
        return competitionDetails;
    }

    public void setCompetitionDetails(CompetitionDetails competitionDetails) {
        this.competitionDetails = competitionDetails;
    }

    public List<CompetitionCode> getCompetitionCodeList() {
        return competitionCodeList;
    }

    public void setCompetitionCodeList(List<CompetitionCode> competitionCodeList) {
        this.competitionCodeList = competitionCodeList;
    }
}
