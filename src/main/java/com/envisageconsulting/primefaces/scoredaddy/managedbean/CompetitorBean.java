package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.SessionUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Address;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionService;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitorService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name="competitorBean")
@RequestScoped
public class CompetitorBean implements Serializable {

    private Competitor competitor = new Competitor();
    private Address address = new Address();

    @ManagedProperty("#{competitorDAO}")
    private CompetitorDAO dao;

    @ManagedProperty("#{competitorService}")
    private CompetitorService competitorService;

    @PostConstruct
    public void init() {
        competitor.setAddress(address);
    }

    public void addCompetitor() {

        try {
            competitorService.addCompetitor(SessionUtils.getAccountId(), getCompetitor());
            dao.getAllCompetitorsByAccountId(SessionUtils.getAccountId()); // This populates the drop down again on add competitor page
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Competitor added successfully!", "INFO MSG"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Competitor was not added!", "ERROR MSG"));
            e.printStackTrace();
        }

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

    public CompetitorService getCompetitorService() {
        return competitorService;
    }

    public void setCompetitorService(CompetitorService competitorService) {
        this.competitorService = competitorService;
    }
}
