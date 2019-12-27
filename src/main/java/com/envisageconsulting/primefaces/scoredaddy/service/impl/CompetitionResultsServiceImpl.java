package com.envisageconsulting.primefaces.scoredaddy.service.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResults;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionResultsService;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CompetitionResultsServiceImpl implements CompetitionResultsService {

    @Autowired
    private CompetitionResultsDAO competitionResultsDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public List<CompetitionResults> getCompetitionResultsByDivisionAndCompetitionId(String division, int competitionId) throws Exception {
        try {
            return competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(division, competitionId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("CompetitionResultsServiceImpl: Failed to get Competition Results By Division and Competition Id!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public void deleteCompetitionResultByCompetitionResultsId(CompetitionResults competitionResults, int competitionResultsId) throws Exception {
        try {
            competitionResultsDAO.addCompetitionResultsHistory(competitionResults, "D");
            competitionResultsDAO.deleteCompetitionResultByCompetitionResultsId(competitionResultsId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("CompetitionResultsServiceImpl: Failed to delete competition result by competition results id!");
        }
    }

    public CompetitionResultsDAO getCompetitionResultsDAO() {
        return competitionResultsDAO;
    }

    public void setCompetitionResultsDAO(CompetitionResultsDAO competitionResultsDAO) {
        this.competitionResultsDAO = competitionResultsDAO;
    }
}
