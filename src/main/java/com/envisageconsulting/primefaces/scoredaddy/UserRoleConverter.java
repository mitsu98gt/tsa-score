package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "userRoleConverter")
@FacesConverter(value = "userRoleConverter")
public class UserRoleConverter implements Converter {

    @ManagedProperty("#{userRoleDataSource}")
    private UserRoleDataSource ds;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        for(UserRole c : ds.getUserRoles()){
            if(c.getCode().equals(value)){
                return c;
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof UserRole){
            UserRole userRole = (UserRole)value;
            return userRole.getCode();
        }
        return "";
    }

    public UserRoleDataSource getDs() {
        return ds;
    }

    public void setDs(UserRoleDataSource ds) {
        this.ds = ds;
    }
}
