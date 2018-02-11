package com.envisageconsulting.primefaces.scoredaddy.service;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResults;

import java.util.List;

public interface CompetitionResultsService {

    public List<CompetitionResults> getCompetitionResultsByDivisionAndCompetitionId(String division, int competitionId) throws Exception;
    public void deleteCompetitionResultByCompetitionResultsId(CompetitionResults competitionResults, int key) throws Exception;

}
