package com.envisageconsulting.primefaces.scoredaddy.service;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

public interface CompetitorService {

    public void addCompetitor(int accountId, Competitor competitor) throws Exception;

}
