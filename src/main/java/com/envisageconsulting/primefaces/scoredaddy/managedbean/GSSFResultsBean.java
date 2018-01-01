package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResults;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@ManagedBean(name="gssfResultsBean")
public class GSSFResultsBean implements Serializable {

    @ManagedProperty("#{competitionResultsDAO}")
    private CompetitionResultsDAO competitionResultsDAO;

    private List<CompetitionResults> competitionStockResultsList;
    private List<CompetitionResults> filtered;

    private String competitionDate;

    @PostConstruct
    public void init() {
        try {
            competitionStockResultsList = competitionResultsDAO.getStockCompetitionResultsByCompetitionId(1);
            competitionDate = getCompetitionDate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCompetitionDate() {
        return "January 6th, 2018";
    }

    public List<CompetitionResults> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<CompetitionResults> filtered) {
        this.filtered = filtered;
    }

    public void setCompetitionResultsDAO(CompetitionResultsDAO competitionResultsDAO) {
        this.competitionResultsDAO = competitionResultsDAO;
    }

    public List<CompetitionResults> getCompetitionStockResultsList() {
        return competitionStockResultsList;
    }
}
