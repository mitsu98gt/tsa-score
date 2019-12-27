package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.SQLConstants;
import com.envisageconsulting.primefaces.scoredaddy.ScoreSheetUtils;
import com.envisageconsulting.primefaces.scoredaddy.SessionUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResults;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionResultsService;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionService;
import org.primefaces.event.RowEditEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="updateScoresBean")
@ViewScoped
public class UpdateScoresBean implements Serializable {

    @ManagedProperty("#{competitionService}")
    private CompetitionService competitionService;

    @ManagedProperty("#{competitionResultsService}")
    private CompetitionResultsService competitionResultsService;

    @ManagedProperty("#{competitionResultsDAO}")
    private CompetitionResultsDAO competitionResultsDAO;

    private List<CompetitionResults> competitionResultsList = new ArrayList<CompetitionResults>();
    private CompetitionResults selectedCompetitiononResults;
    private List<CompetitionResults> filtered;
    private List<String> divisionCodeList = new ArrayList<String>();
    private Competition competition = new Competition();
    private String division;
    private boolean renderScores;
    private String accountName;

    @PostConstruct
    public void init() {
        renderScores = false;
        accountName = SessionUtils.getAccountName();
    }

    public void onCompetitionChange() {
        try {
            divisionCodeList = competitionService.getDivisionCodesByCompetitionCode(Integer.valueOf(competition.getCompetitionDetails().getCompetitionCode().getCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getScores() {
        renderScores = true;
        try {
            competitionResultsList = competitionResultsService.getCompetitionResultsByDivisionAndCompetitionId(getConvertedDivisionCode(division), Integer.valueOf(competition.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteRow() {
        if (null == selectedCompetitiononResults) {
            FacesContext.getCurrentInstance().addMessage("msgs", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Please Select A Row!"));
        } else {
            try {
                competitionResultsService.deleteCompetitionResultByCompetitionResultsId(selectedCompetitiononResults, selectedCompetitiononResults.getCompetitionResultsId());
                competitionResultsList = competitionResultsService.getCompetitionResultsByDivisionAndCompetitionId(getConvertedDivisionCode(division), Integer.valueOf(competition.getId()));
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Delete Successful!"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Info:", "Delete UnSuccessful!"));
                ex.printStackTrace();
            }
        }
    }

    public void onRowEdit(RowEditEvent event) {
        CompetitionResults results = (CompetitionResults) event.getObject();
        if (ScoreSheetUtils.validateScoreSheet(results.getGssfIndoorScoreSheet())) {
            try {
                CompetitionResults currentResults = competitionResultsDAO.getCompetitionResultsByCompetitionResultsId(results.getCompetitionResultsId());
                competitionResultsDAO.addCompetitionResultsHistory(currentResults, "U");
                competitionResultsDAO.updateCompetitionResults(results);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Update Successful!"));
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Update Failed!"));
            }
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Update Cancelled!"));
    }

    public String getConvertedDivisionCode(String division) {

        if (division.equals("GSSF_UNLIMITED")) {
            return SQLConstants.UNLIMITED_DIVISION;
        }
        if (division.equals("GSSF_STOCK")) {
            return SQLConstants.STOCK_DIVISION;
        }
        if (division.equals("GSSF_POCKET")) {
            return SQLConstants.POCKET_DIVISION;
        }
        if (division.equals("GSSF_RIMFIRE")) {
            return SQLConstants.RIMFIRE_DIVISION;
        }

        return null;
    }

    public List<CompetitionResults> getCompetitionResultsList() {
        return competitionResultsList;
    }

    public void setCompetitionResultsList(List<CompetitionResults> competitionResultsList) {
        this.competitionResultsList = competitionResultsList;
    }

    public CompetitionResults getSelectedCompetitiononResults() {
        return selectedCompetitiononResults;
    }

    public void setSelectedCompetitiononResults(CompetitionResults selectedCompetitiononResults) {
        this.selectedCompetitiononResults = selectedCompetitiononResults;
    }

    public List<CompetitionResults> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<CompetitionResults> filtered) {
        this.filtered = filtered;
    }

    public void setCompetitionService(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    public void setCompetitionResultsService(CompetitionResultsService competitionResultsService) {
        this.competitionResultsService = competitionResultsService;
    }

    public List<String> getDivisionCodeList() {
        return divisionCodeList;
    }

    public void setDivisionCodeList(List<String> divisionCodeList) {
        this.divisionCodeList = divisionCodeList;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public boolean isRenderScores() {
        return renderScores;
    }

    public void setRenderScores(boolean renderScores) {
        this.renderScores = renderScores;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public CompetitionResultsDAO getCompetitionResultsDAO() {
        return competitionResultsDAO;
    }

    public void setCompetitionResultsDAO(CompetitionResultsDAO competitionResultsDAO) {
        this.competitionResultsDAO = competitionResultsDAO;
    }
}
