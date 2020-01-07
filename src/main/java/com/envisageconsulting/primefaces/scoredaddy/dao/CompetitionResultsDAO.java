package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.*;

import java.util.List;
import java.util.Map;

public interface CompetitionResultsDAO {

    public CompetitionResults getCompetitionResultsByCompetitionResultsId(int competitionResultsId, String division) throws Exception;
    public List<CompetitionResults> getCompetitionResultsForUpdate(String division, int competitionId) throws Exception;
    public List<CompetitionResults> getCompetitionResultsByDivisionAndCompetitionId(String division, int competitionId, String additionalEntries) throws Exception;
    public List<Competitor> getCompetitorIdByCompetitionAndDivision(int competition_id, String division, String additionalEntries) throws Exception;
    public List<Firearm> getCompetitorFirearmByCompetitionAndDivision(int competitor_id, int competition_id, String division, String additionalEntries) throws Exception;
    public CompetitionResultsRow getCompetitionResultsByCompetitionCompetitorFirearmDivision(int competition_id, int competitor_id, int firearm_id, String division, String additionalEntries) throws Exception;
    public void addCompetitionResults(CompetitionResults competitionResults, int rank) throws Exception;
    public void updateCompetitionResultsRank(int id, int rank) throws Exception;
    public void addCompetitionResultsHistory(CompetitionResults competitionResults, String historyType) throws Exception;
    public void updateCompetitionResults(CompetitionResults competitionResults) throws  Exception;
    public void deleteCompetitionResultByCompetitionResultsId(int key) throws Exception;
    public int getCompetitorNumberOfDesignatedEntriesByCompetitionAndDivision(int competitionId, int competitorId, String division) throws Exception;
    public int getCompetitorNumberOfDesignatedEntriesByTournamentAndDivision(int competitionId, int competitorId, String division) throws Exception;
    public List<Firearm> getCompetitorAdditionalEntriesByFirearms(int competitionId, int competitorId, String division) throws Exception;
    public List<Firearm> getCompetitorDesignatedFirearmByTournamentAndDivision(int competitorId, int competitionId, String division, String additionalEntries) throws Exception;
    public List<CompetitorRank> getCompetitorRanks(int competitionId, int competitorId, String division, String additionalEntry) throws  Exception;
}
