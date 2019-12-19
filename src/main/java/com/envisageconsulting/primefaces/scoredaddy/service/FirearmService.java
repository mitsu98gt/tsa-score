package com.envisageconsulting.primefaces.scoredaddy.service;

import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;
import com.envisageconsulting.primefaces.scoredaddy.domain.FirearmBrand;

import java.util.List;

public interface FirearmService {

    public List<FirearmBrand> getAllFirearmBrands() throws Exception;
    public void insertFirearm(Firearm firearm) throws Exception;
    public void insertFirearmBrand(String brand) throws Exception;

}
