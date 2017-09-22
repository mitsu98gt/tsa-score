package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.dao.impl.UserDAOImpl;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userRoleDataSource")
@SessionScoped
public class UserRoleDataSource {

    @ManagedProperty("#{userRoleDAOImplService}")
    private UserDAOImpl userRoleDAO;

    public List<UserRole> userRoles = new ArrayList<UserRole>();

    public UserRoleDataSource() {
        //userRoles = userRoleDAO.getAllUserRoles();
    }

    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoleDAO(UserDAOImpl userRoleDAO) {
        this.userRoleDAO = userRoleDAO;
    }
}
