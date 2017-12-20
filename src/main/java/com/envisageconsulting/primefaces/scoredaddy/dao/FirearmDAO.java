package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;

import java.util.List;

public interface FirearmDAO {

    public List<Firearm> getFirearmForScoreSheet();

}
