package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.dao.FirearmDAO;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name="firearmBean")
@SessionScoped
public class FirearmBean implements Serializable {

    @ManagedProperty("#{firearmDAO}")
    private FirearmDAO dao;

    @PostConstruct
    public void init() {

    }

    public void setDao(FirearmDAO dao) {
        this.dao = dao;
    }
}
