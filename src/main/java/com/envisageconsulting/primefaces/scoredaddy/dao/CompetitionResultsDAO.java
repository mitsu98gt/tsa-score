package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResults;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResultsRow;

import java.util.List;

public interface CompetitionResultsDAO {

    public List<CompetitionResults> getCompetitionResultsByDivisionAndCompetitionId(String division, int competitionId) throws Exception;
    public CompetitionResultsRow getCompetitionResultsByCompetitionAndDivision(int competition_id, String division) throws Exception;
    public void addCompetitionResults(CompetitionResults competitionResults) throws Exception;

}
