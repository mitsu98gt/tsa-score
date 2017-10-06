package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name="competitionBean")
@SessionScoped
public class CompetitionBean implements Serializable {

    Competition competition = new Competition();

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO dao;

    @PostConstruct
    public void init() {

    }

    public void addCompetition() {
        competition.setAccountId("1"); //TODO Testing only, Remove this at some point.
        dao.addCompetition(getCompetition());
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
}
