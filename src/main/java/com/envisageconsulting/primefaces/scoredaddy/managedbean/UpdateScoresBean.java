package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="updateScoresBean")
@ViewScoped
public class UpdateScoresBean {

    @ManagedProperty("#{competitionService}")
    private CompetitionService competitionService;

    List<String> divisionCodeList = new ArrayList<String>();
    Competition competition = new Competition();
    String division;

    @PostConstruct
    public void init() {

    }

    public void onCompetitionChange() {
        try {
            divisionCodeList = competitionService.getDivisionCodesByCompetitionCode(Integer.valueOf(competition.getCompetitionDetails().getCompetitionCode().getCode()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getScores() {

    }

    public void setCompetitionService(CompetitionService competitionService) {
        this.competitionService = competitionService;
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
}
