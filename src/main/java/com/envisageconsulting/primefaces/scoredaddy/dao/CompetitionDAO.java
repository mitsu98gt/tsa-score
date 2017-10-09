package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionCode;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;
import com.envisageconsulting.primefaces.scoredaddy.domain.CourseCode;

import java.util.List;

public interface CompetitionDAO {

    public void addCompetition(Competition competition);
    public void addCompetitionDetails(CompetitionDetails competitionDetails);
    public List<CompetitionCode> getAllCompetitionCodes();
    public List<CourseCode> getAllCourseCodes();

}
