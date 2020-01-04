package com.envisageconsulting.primefaces.scoredaddy.service;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;

import java.util.List;

public interface CompetitionService {

    public void insertCompetition(Competition competition, CompetitionDetails competitionDetails) throws Exception;
    public List<String> getDivisionCodesByCompetitionCode(int competitionCode) throws Exception;
    public void updateCompetitionStatus(String status, int competitionId) throws Exception;
}
