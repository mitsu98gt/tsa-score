package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name="competitorBean")
@SessionScoped
public class CompetitorBean implements Serializable {

    private Competitor competitor = new Competitor();

    @ManagedProperty("#{competitorDAO}")
    private CompetitorDAO dao;

    @PostConstruct
    public void init() {

    }

    public void addCompetitor() {


    }
    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public void setDao(CompetitorDAO dao) {
        this.dao = dao;
    }
}
