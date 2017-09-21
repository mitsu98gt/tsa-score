package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name="competitorBean")
@SessionScoped
public class CompetitorBean implements Serializable {

    private Competitor competitor = new Competitor();

    @PostConstruct
    public void init() {

    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }
}
