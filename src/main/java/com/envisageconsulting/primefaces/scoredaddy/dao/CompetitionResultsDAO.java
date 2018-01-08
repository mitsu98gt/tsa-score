package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResults;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResultsRow;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;

import java.util.List;

public interface CompetitionResultsDAO {

    public List<CompetitionResults> getCompetitionResultsByDivisionAndCompetitionId(String division, int competitionId) throws Exception;
    public List<Competitor> getCompetitorIdByCompetitionAndDivision(int competition_id, String division) throws Exception;
    public List<Firearm> getCompetitorFirearmByCompetitionAndDivision(int competitor_id, int competition_id, String division) throws Exception;
    public CompetitionResultsRow getCompetitionResultsByCompetitionCompetitorFirearmDivision(int competition_id, int competitor_id, int firearm_id, String division) throws Exception;
    public void addCompetitionResults(CompetitionResults competitionResults) throws Exception;

}
