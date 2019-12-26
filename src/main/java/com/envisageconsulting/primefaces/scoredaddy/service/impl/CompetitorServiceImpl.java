package com.envisageconsulting.primefaces.scoredaddy.service.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetitorServiceImpl implements CompetitorService {

    @Autowired
    private CompetitorDAO competitorDAO;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public void addCompetitor(int accountId, Competitor competitor) throws Exception {
        try {
            int competitorId = competitorDAO.addCompetitor(competitor);
            competitorDAO.addCompetitorAccount(competitorId, accountId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("CompetitorServiceImpl: Failed to insert Competitor!");
        }
    }

    public CompetitorDAO getCompetitorDAO() {
        return competitorDAO;
    }

    public void setCompetitorDAO(CompetitorDAO competitorDAO) {
        this.competitorDAO = competitorDAO;
    }
}
