package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.SessionUtils;
import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionService;
import com.envisageconsulting.primefaces.scoredaddy.service.TournamentService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ManagedBean(name="tournamentBean")
@RequestScoped
public class TournamentBean implements Serializable {

    Tournament tournament = new Tournament();

    @ManagedProperty("#{tournamentService}")
    private TournamentService tournamentService;

    @PostConstruct
    public void init() {

    }

    public void insertTournament() {

        try {
            tournament.setAccount_id(SessionUtils.getAccountId());
            tournamentService.insertTournament(tournament);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tournament added successfully!", "INFO MSG"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Add Tournament Failed!", "ERROR MSG"));
            e.printStackTrace();
        }

    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public TournamentService getTournamentService() {
        return tournamentService;
    }

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }
}
