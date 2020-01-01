package com.envisageconsulting.primefaces.scoredaddy.service.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.TournamentDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.DashboardRow;
import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;
import com.envisageconsulting.primefaces.scoredaddy.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentDAO tournamentDAO;

    public List<DashboardRow> getDashboardRows(int accountId) throws  Exception {
        try{
            return tournamentDAO.getDashboardRows(accountId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("TournamentServiceImpl: Failed to get dashboard rows!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public void insertTournament(Tournament tournament) throws Exception {
        try {
            tournamentDAO.addTournament(tournament);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("TournamentServiceImpl: Failed to insert Tournament!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public List<Tournament> getAllTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception {
        try {
            return tournamentDAO.getAllTournamentsByAccountIdAndStatus(accountId, status);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("TournamentServiceImpl: Failed to get all Tournaments!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public List<Tournament> getAllGlockTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception {
        try {
            return tournamentDAO.getAllGlockTournamentsByAccountIdAndStatus(accountId, status);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("TournamentServiceImpl: Failed to get all Glock Tournaments!");
        }
    }

    public TournamentDAO getTournamentDAO() {
        return tournamentDAO;
    }

    public void setTournamentDAO(TournamentDAO tournamentDAO) {
        this.tournamentDAO = tournamentDAO;
    }
}
