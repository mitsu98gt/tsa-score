package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.SessionUtils;
import com.envisageconsulting.primefaces.scoredaddy.domain.DashboardRow;
import com.envisageconsulting.primefaces.scoredaddy.service.TournamentService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean(name="dashboardBean")
public class DashboardBean implements Serializable {

    @ManagedProperty("#{tournamentService}")
    private TournamentService tournamentService;

    private List<DashboardRow> dashboardRowList;

    @PostConstruct
    public void init() {
        try {
            dashboardRowList = tournamentService.getDashboardRows(SessionUtils.getAccountId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TournamentService getTournamentService() {
        return tournamentService;
    }

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public List<DashboardRow> getDashboardRowList() {
        return dashboardRowList;
    }

    public void setDashboardRowList(List<DashboardRow> dashboardRowList) {
        this.dashboardRowList = dashboardRowList;
    }
}
