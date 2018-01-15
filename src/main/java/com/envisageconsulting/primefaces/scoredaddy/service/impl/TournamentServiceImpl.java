package com.envisageconsulting.primefaces.scoredaddy.service.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.TournamentDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;
import com.envisageconsulting.primefaces.scoredaddy.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentDAO tournamentDAO;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public void insertTournament(Tournament tournament) throws Exception {
        try {
            tournamentDAO.addTournament(tournament);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("TournamentServiceImpl: Failed to insert Tournament!");
        }
    }

    public TournamentDAO getTournamentDAO() {
        return tournamentDAO;
    }

    public void setTournamentDAO(TournamentDAO tournamentDAO) {
        this.tournamentDAO = tournamentDAO;
    }
}
