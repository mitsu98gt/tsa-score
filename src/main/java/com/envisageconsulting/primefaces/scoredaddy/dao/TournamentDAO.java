package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.DashboardRow;
import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;

import java.util.List;

public interface TournamentDAO {

    public List<Tournament> getAllTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception;
    public List<Tournament> getAllGlockTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception;
    public List<Tournament> getAllBullseyeTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception;
    public void addTournament(Tournament tournament) throws Exception;
    public List<DashboardRow> getDashboardRows(int accountId) throws Exception;
}
