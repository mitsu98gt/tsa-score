package com.envisageconsulting.primefaces.scoredaddy.service.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.FirearmDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.TournamentDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;
import com.envisageconsulting.primefaces.scoredaddy.domain.FirearmBrand;
import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;
import com.envisageconsulting.primefaces.scoredaddy.service.FirearmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class FirearmServiceImpl implements FirearmService {

    @Autowired
    private FirearmDAO firearmDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public List<FirearmBrand> getAllFirearmBrands() throws Exception {
        try {
            return firearmDAO.getAllFirearmBrands();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("FirearmServiceImpl: Failed to get all Firearm brands!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
    public void insertFirearm(Firearm firearm) throws Exception {
        try {
            firearmDAO.addFirearm(firearm);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("FirearmServiceImpl: Failed to insert Firearm!");
        }
    }

    public FirearmDAO getFirearmDAO() {
        return firearmDAO;
    }

    public void setFirearmDAO(FirearmDAO firearmDAO) {
        this.firearmDAO = firearmDAO;
    }
}
