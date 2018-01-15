package com.envisageconsulting.primefaces.scoredaddy.service.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetitionServiceImpl implements CompetitionService {

    @Autowired
    private CompetitionDAO competitionDAO;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class)
    public void insertCompetition() {

    }

    public CompetitionDAO getCompetitionDAO() {
        return competitionDAO;
    }

    public void setCompetitionDAO(CompetitionDAO competitionDAO) {
        this.competitionDAO = competitionDAO;
    }
}
