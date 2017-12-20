package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "firearmConverter")
@FacesConverter(value = "firearmConverter")
public class FirearmConverter implements Converter {

    @ManagedProperty("#{firearmDataSource}")
    private FirearmDataSource ds;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for(Firearm f : ds.getFirearms()){
            if(f.getId().equals(value)){
                return f;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof Firearm){
            Firearm firearm = (Firearm)value;
            return firearm.getId();
        }
        return "";
    }

    public FirearmDataSource getDs() {
        return ds;
    }

    public void setDs(FirearmDataSource ds) {
        this.ds = ds;
    }
}
