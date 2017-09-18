package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.User;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.util.List;

@SessionScoped
@ManagedBean(name = "userBean")
public class UserBean {

    private User user;
    private List<UserRole> roles;

    @ManagedProperty("#{userRoleDataSource}")
    private UserRoleDataSource ds;

    @PostConstruct
    public void init() {
        roles = ds.getUserRoles();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserRoleDataSource getDs() {
        return ds;
    }

    public void setDs(UserRoleDataSource ds) {
        this.ds = ds;
    }
}
