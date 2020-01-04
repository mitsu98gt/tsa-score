package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.SessionUtils;
import com.envisageconsulting.primefaces.scoredaddy.domain.DashboardRow;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionService;
import com.envisageconsulting.primefaces.scoredaddy.service.TournamentService;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ViewScoped
@ManagedBean(name="dashboardBean")
public class DashboardBean implements Serializable {

    @ManagedProperty("#{tournamentService}")
    private TournamentService tournamentService;

    @ManagedProperty("#{competitionService}")
    private CompetitionService competitionService;

    private List<DashboardRow> dashboardRowList;

    private List<String> status;

    @PostConstruct
    public void init() {
        try {
            dashboardRowList = tournamentService.getDashboardRows(SessionUtils.getAccountId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onStatusEdit(RowEditEvent event) {

        DashboardRow dashboardRow = (DashboardRow) event.getObject();
        try {
            competitionService.updateCompetitionStatus(getStatusCode(dashboardRow.getCompetitionStatusDescription()), dashboardRow.getCompetitionId());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Update Success!"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Update Failed!"));
        }
    }

    public String getStatusCode(String statusDescription) {
        if (statusDescription.equals("Not Started")) {
            return "N";
        }
        if (statusDescription.equals("In Progress")) {
            return "I";
        }
        return "C";
    }

    public void onStatusEditCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Update Cancelled!"));
    }

    public TournamentService getTournamentService() {
        return tournamentService;
    }

    public void setTournamentService(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    public CompetitionService getCompetitionService() {
        return competitionService;
    }

    public void setCompetitionService(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    public List<String> getStatus() {
        return Arrays.asList("Not Started", "In Progress", "Complete");
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<DashboardRow> getDashboardRowList() {
        return dashboardRowList;
    }

    public void setDashboardRowList(List<DashboardRow> dashboardRowList) {
        this.dashboardRowList = dashboardRowList;
    }
}
