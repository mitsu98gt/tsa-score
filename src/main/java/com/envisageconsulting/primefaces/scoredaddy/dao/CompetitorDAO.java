package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

import java.util.List;

public interface CompetitorDAO {

    public List<Competitor> getCompetitorsForScoreSheet();
    public void addCompetitor(Competitor competitor);

}
