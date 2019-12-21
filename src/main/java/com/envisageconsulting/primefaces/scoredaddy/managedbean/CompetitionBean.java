package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionCode;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;
import com.envisageconsulting.primefaces.scoredaddy.domain.CourseCode;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ManagedBean(name="competitionBean")
@SessionScoped
public class CompetitionBean implements Serializable {

    Competition competition = new Competition();
    CompetitionDetails competitionDetails =  new CompetitionDetails();
    List<CompetitionCode> competitionCodeList = new ArrayList<CompetitionCode>();
    List<CourseCode> courseCodeList = new ArrayList<CourseCode>();

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO dao;

    @PostConstruct
    public void init() {
        getCompetitionDetails().setCompetitionCode(new CompetitionCode());
        try {
            setCompetitionCodeList(dao.getAllCompetitionCodes());
            setCourseCodeList(dao.getAllCourseCodes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCompetition() {
        try {
            competition.setAccountId(getAccountIdFromSession());
            dao.addCompetition(getCompetition());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Competition added successfully!", "INFO MSG"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCompetitionDetails() {
        competitionDetails.setCompetitionDetailsId("1"); //TODO Testing only, Remove this at some point.
        try {
            dao.addCompetitionDetails(getCompetitionDetails());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Competition Details added successfully!", "INFO MSG"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getAccountIdFromSession() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        return (int) sessionMap.get("accountId");
    }

    public void setDao(CompetitionDAO dao) {
        this.dao = dao;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public CompetitionDetails getCompetitionDetails() {
        return competitionDetails;
    }

    public void setCompetitionDetails(CompetitionDetails competitionDetails) {
        this.competitionDetails = competitionDetails;
    }

    public List<CompetitionCode> getCompetitionCodeList() {
        return competitionCodeList;
    }

    public void setCompetitionCodeList(List<CompetitionCode> competitionCodeList) {
        this.competitionCodeList = competitionCodeList;
    }

    public List<CourseCode> getCourseCodeList() {
        return courseCodeList;
    }

    public void setCourseCodeList(List<CourseCode> courseCodeList) {
        this.courseCodeList = courseCodeList;
    }
}
