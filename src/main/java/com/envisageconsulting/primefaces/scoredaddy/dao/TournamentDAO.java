package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;

import java.util.List;

public interface TournamentDAO {

    public List<Tournament> getAllTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception;
    public void addTournament(Tournament tournament) throws Exception;

}
