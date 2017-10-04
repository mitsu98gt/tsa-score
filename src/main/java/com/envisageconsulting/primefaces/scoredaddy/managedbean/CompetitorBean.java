package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Address;
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
    private Address address = new Address();

    @ManagedProperty("#{competitorDAO}")
    private CompetitorDAO dao;

    @PostConstruct
    public void init() {

        competitor.setAddress(address);
    }

    public void addCompetitor() {

        dao.addCompetitor(getCompetitor());

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
