package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ManagedBean(name="registerBean")
@RequestScoped
public class RegisterBean implements Serializable {

    private List<Competitor> allCompetitors;
    private List<Competition> allCompetitions;

    @ManagedProperty("#{competitorDAO}")
    private CompetitorDAO competitorDAO;

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO competitionDAO;

    private Competitor competitor;
    private Competition competition;

    @PostConstruct
    public void init() {
        try {
            allCompetitors = competitorDAO.getAllCompetitors();
            allCompetitions = competitionDAO.getCompetitionsByAccountIdAndStatus(getAccountIdFromSession(), "I");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerCompetitor() {
        try {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Competitor registered successfully!", "INFO MSG"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Competitor was not registered!", "ERROR MSG"));
            e.printStackTrace();
        }
    }

    public List<Competitor> complete(String query){
        return queryByName(query);
    }

    public List<Competitor> queryByName(String name){
        // Assumed search using the contains
        List<Competitor> queried = new ArrayList<Competitor>();
        for(Competitor competitor: this.allCompetitors){
            if(competitor.getFullName().toLowerCase().contains(name.toLowerCase())){
                queried.add(competitor);
            }
        }
        return queried;
    }

    public int getAccountIdFromSession() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        return (int) sessionMap.get("accountId");
    }

    public List<Competitor> getAllCompetitors() {
        return allCompetitors;
    }

    public List<Competition> getAllCompetitions() {
        return allCompetitions;
    }

    public CompetitorDAO getCompetitorDAO() {
        return competitorDAO;
    }

    public void setCompetitorDAO(CompetitorDAO competitorDAO) {
        this.competitorDAO = competitorDAO;
    }

    public CompetitionDAO getCompetitionDAO() {
        return competitionDAO;
    }

    public void setCompetitionDAO(CompetitionDAO competitionDAO) {
        this.competitionDAO = competitionDAO;
    }

    public Competitor getCompetitor() {
        return competitor;
    }

    public void setCompetitor(Competitor competitor) {
        this.competitor = competitor;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}
