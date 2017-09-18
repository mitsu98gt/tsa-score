package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.dao.UserRoleDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.User;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable {

    private User user;
    private List<UserRole> roles;

    @ManagedProperty("#{userRoleDAO}")
    private UserRoleDAO dao;

    @PostConstruct
    public void init() {
        //roles = dao.getAllUserRoles();
    }

    public String printMsgFromSpring() {
        return dao.getMessage();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRoleDAO getDao() {
        return dao;
    }

    public void setDao(UserRoleDAO dao) {
        this.dao = dao;
    }
}
