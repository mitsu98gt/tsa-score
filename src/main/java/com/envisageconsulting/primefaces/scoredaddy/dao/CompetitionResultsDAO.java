package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResults;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResultsRow;

import java.util.List;

public interface CompetitionResultsDAO {

    public List<CompetitionResults> getCompetitionResultsByDivisionAndCompetitionId(String division, int competitionId) throws Exception;
    public CompetitionResultsRow getAverageTwoCompetitionResultsByCompetitorIdAndDivision(int competitorId, String divisionName, int division, int competitionId1, int competitionId2) throws Exception;
    public void addCompetitionResults(CompetitionResults competitionResults) throws Exception;

}
