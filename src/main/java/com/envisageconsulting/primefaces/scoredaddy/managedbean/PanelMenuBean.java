package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.SessionUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Address;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Session;
import java.io.Serializable;

@ManagedBean(name="panelMenuBean")
@SessionScoped
public class PanelMenuBean implements Serializable {

    private boolean adminExpanded;
    private boolean tournamentExpanded;
    private boolean competitionExpanded;
    private boolean competitorExpanded;
    private boolean firearmExpanded;
    private boolean gssfExpanded;
    private boolean bullseyeExpanded;
    private boolean scoresExpanded;
    private boolean admin;

    @PostConstruct
    public void init() {
    }

    public boolean isAdmin() {
        if (null == Integer.valueOf(SessionUtils.getAccountId())) {
            return false;
        }
        return SessionUtils.getAccountId() == 1 ? true : false;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdminExpanded() {
        return adminExpanded;
    }

    public void setAdminExpanded(boolean adminExpanded) {
        this.adminExpanded = adminExpanded;
    }

    public boolean isTournamentExpanded() {
        return tournamentExpanded;
    }

    public void setTournamentExpanded(boolean tournamentExpanded) {
        this.tournamentExpanded = tournamentExpanded;
    }

    public boolean isCompetitionExpanded() {
        return competitionExpanded;
    }

    public void setCompetitionExpanded(boolean competitionExpanded) {
        this.competitionExpanded = competitionExpanded;
    }

    public boolean isFirearmExpanded() {
        return firearmExpanded;
    }

    public void setFirearmExpanded(boolean firearmExpanded) {
        this.firearmExpanded = firearmExpanded;
    }

    public boolean isGssfExpanded() {
        return gssfExpanded;
    }

    public void setGssfExpanded(boolean gssfExpanded) {
        this.gssfExpanded = gssfExpanded;
    }

    public boolean isBullseyeExpanded() {
        return bullseyeExpanded;
    }

    public void setBullseyeExpanded(boolean bullseyeExpanded) {
        this.bullseyeExpanded = bullseyeExpanded;
    }

    public boolean isScoresExpanded() {
        return scoresExpanded;
    }

    public void setScoresExpanded(boolean scoresExpanded) {
        this.scoresExpanded = scoresExpanded;
    }

    public boolean isCompetitorExpanded() {
        return competitorExpanded;
    }

    public void setCompetitorExpanded(boolean competitorExpanded) {
        this.competitorExpanded = competitorExpanded;
    }
}
