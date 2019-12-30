package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionCode;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;
import com.envisageconsulting.primefaces.scoredaddy.domain.CourseCode;

import java.util.List;

public interface CompetitionDAO {

    public int getNumberOfCompetitionsInTournament(int tournamentId) throws Exception;
    public int addCompetition(Competition competition) throws Exception;
    public void addCompetitionDetails(CompetitionDetails competitionDetails) throws Exception;
    public List<CompetitionCode> getAllCompetitionCodes() throws Exception;
    public List<CourseCode> getAllCourseCodes() throws Exception;
    public List<Competition> getCompetitionsByTournamentIdAndStatus(int tournamentId, String status) throws Exception;
    public List<Competition> getAllCompetitionsByAccountIdAndStatus(int accountId, String status) throws Exception;
    public List<Competition> getGlockCompetitionsByAccountIdAndStatus(int accountId, String status) throws Exception;
    public List<Competition> getBullseyeCompetitionsByAccountIdAndStatus(int accountId, String status) throws Exception;
    public List<Competition> getAllCompetitionsByAccountId(int accountId) throws Exception;
    public void addCompetitionCompetitors(int competitionId, int competitorId) throws Exception;
    public void updateCompetitionStatus(String status, int competitionId) throws Exception;
    public List<String> getDivisionCodesByCompetitionCode(int competitionCode) throws Exception;

}
