package com.envisageconsulting.primefaces.scoredaddy.service;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;

public interface CompetitionService {

    public void insertCompetition(Competition competition, CompetitionDetails competitionDetails) throws Exception;

}
