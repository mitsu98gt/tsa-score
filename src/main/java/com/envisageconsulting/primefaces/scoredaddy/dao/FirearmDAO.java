package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;
import com.envisageconsulting.primefaces.scoredaddy.domain.FirearmBrand;

import java.util.List;

public interface FirearmDAO {

    public Firearm getFirearmById(String id) throws Exception;
    public List<Firearm> getGlockFirearmForScoreSheet() throws Exception;
    public List<Firearm> getAllFirearmsForScoreSheet() throws Exception;
    public List<FirearmBrand> getAllFirearmBrands() throws Exception;
    public void addFirearm(Firearm firearm) throws  Exception;
    public void addFirearmBrand(String brand) throws  Exception;

}
