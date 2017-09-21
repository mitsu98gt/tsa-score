package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean(name="firearmBean")
@SessionScoped
public class FirearmBean implements Serializable {

    @PostConstruct
    public void init() {

    }

}
