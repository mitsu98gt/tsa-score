package com.envisageconsulting.primefaces.scoredaddy.service;

import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;

import java.util.List;

public interface TournamentService {

    public List<Tournament> getAllTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception;
    public void insertTournament(Tournament tournament) throws Exception;

}
