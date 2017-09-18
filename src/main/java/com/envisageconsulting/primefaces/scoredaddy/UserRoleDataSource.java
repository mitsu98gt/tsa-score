package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.dao.UserRoleDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.impl.UserRoleDAOImpl;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.FacesComponent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("session")
//@ManagedBean(name = "userRoleDataSource")
//@SessionScoped
public class UserRoleDataSource {

    @Autowired
    private UserRoleDAOImpl userRoleDAO;

    public List<UserRole> userRoles = new ArrayList<UserRole>();

    public UserRoleDataSource() {
        userRoles = userRoleDAO.getAllUserRoles();
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoleDAO(UserRoleDAOImpl userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }
}
