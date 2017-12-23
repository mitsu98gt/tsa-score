package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionCode;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;
import com.envisageconsulting.primefaces.scoredaddy.domain.CourseCode;

import java.util.List;

public interface CompetitionDAO {

    public void addCompetition(Competition competition) throws Exception;
    public void addCompetitionDetails(CompetitionDetails competitionDetails) throws Exception;
    public List<CompetitionCode> getAllCompetitionCodes() throws Exception;
    public List<CourseCode> getAllCourseCodes() throws Exception;

}
