package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionCode;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;

import java.util.List;

public interface CompetitionDAO {

    public void addCompetition(Competition competition);
    public void addCompetitionDetails(CompetitionDetails competitionDetails);
    public List<CompetitionCode> getAllCompetitionCodes();

}
