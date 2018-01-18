package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.SessionUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.FirearmDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;
import com.envisageconsulting.primefaces.scoredaddy.domain.FirearmBrand;
import com.envisageconsulting.primefaces.scoredaddy.service.FirearmService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="firearmBean")
@RequestScoped
public class FirearmBean implements Serializable {

    List<FirearmBrand> firearmBrandList = new ArrayList<FirearmBrand>();
    Firearm firearm = new Firearm();
    FirearmBrand firearmBrand = new FirearmBrand();
    String newBrand = "";

    @ManagedProperty("#{firearmService}")
    private FirearmService firearmService;

    @PostConstruct
    public void init() {
        try {
            setFirearmBrandList(firearmService.getAllFirearmBrands());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertNewBrand() throws IOException {
        try {
            firearmService.insertFirearmBrand(newBrand);
            setFirearmBrandList(firearmService.getAllFirearmBrands());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Brand added successfully!", "INFO MSG"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to add Brand!", "ERROR MSG"));
            e.printStackTrace();
        }
    }

    public void insertFirearm() {
        try {
            firearm.setBrand(firearmBrand.getId());
            firearmService.insertFirearm(firearm);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Firearm added successfully!", "INFO MSG"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to add Firearm!", "ERROR MSG"));
            e.printStackTrace();
        }
    }

    public Firearm getFirearm() {
        return firearm;
    }

    public void setFirearm(Firearm firearm) {
        this.firearm = firearm;
    }

    public List<FirearmBrand> getFirearmBrandList() {
        return firearmBrandList;
    }

    public void setFirearmBrandList(List<FirearmBrand> firearmBrandList) {
        this.firearmBrandList = firearmBrandList;
    }

    public FirearmService getFirearmService() {
        return firearmService;
    }

    public void setFirearmService(FirearmService firearmService) {
        this.firearmService = firearmService;
    }

    public FirearmBrand getFirearmBrand() {
        return firearmBrand;
    }

    public void setFirearmBrand(FirearmBrand firearmBrand) {
        this.firearmBrand = firearmBrand;
    }

    public String getNewBrand() {
        return newBrand;
    }

    public void setNewBrand(String newBrand) {
        this.newBrand = newBrand;
    }
}
