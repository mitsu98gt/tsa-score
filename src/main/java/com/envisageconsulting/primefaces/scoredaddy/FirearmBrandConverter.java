package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.dao.FirearmDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.FirearmBrand;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "firearmBrandConverter")
@FacesConverter(value = "firearmBrandConverter")
public class FirearmBrandConverter implements Converter {

    @ManagedProperty("#{firearmDAO}")
    private FirearmDAO firearmDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            for(FirearmBrand c : firearmDAO.getAllFirearmBrands()){
                if(c.getId().equals(value)){
                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof FirearmBrand){
            FirearmBrand firearmBrand = (FirearmBrand)value;
            return firearmBrand.getId();
        }
        return "";
    }

    public FirearmDAO getFirearmDAO() {
        return firearmDAO;
    }

    public void setFirearmDAO(FirearmDAO firearmDAO) {
        this.firearmDAO = firearmDAO;
    }
}
