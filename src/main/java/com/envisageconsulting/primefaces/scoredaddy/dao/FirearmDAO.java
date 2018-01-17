package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;
import com.envisageconsulting.primefaces.scoredaddy.domain.FirearmBrand;

import java.util.List;

public interface FirearmDAO {

    public List<Firearm> getGlockFirearmForScoreSheet() throws Exception;
    public List<FirearmBrand> getAllFirearmBrands() throws Exception;
    public void addFirearm(Firearm firearm) throws  Exception;

}
