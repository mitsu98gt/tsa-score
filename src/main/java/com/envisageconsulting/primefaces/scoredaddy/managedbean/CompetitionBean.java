package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name="competitionBean")
@SessionScoped
public class CompetitionBean implements Serializable {

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO dao;

    @PostConstruct
    public void init() {

    }

    public void addCompetition() {

    }

    public void setDao(CompetitionDAO dao) {
        this.dao = dao;
    }
}
