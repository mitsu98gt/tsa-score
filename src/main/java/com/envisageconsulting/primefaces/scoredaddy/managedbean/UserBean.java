package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.Encryption;
import com.envisageconsulting.primefaces.scoredaddy.dao.UserDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.User;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="userBean")
@SessionScoped
public class UserBean implements Serializable {

    private User user = new User();
    private List<UserRole> roles =  new ArrayList<UserRole>();
    private String role;
    private String password2;

    @ManagedProperty("#{userDAO}")
    private UserDAO dao;

    @PostConstruct
    public void init() {
        try {
            setRoles(dao.getAllUserRoles());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser() throws InvalidKeySpecException, NoSuchAlgorithmException {
        if (!isPasswordsMatch()){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match.", "ERROR MSG"));
        } else {
            UserRole userRole =  new UserRole();
            userRole.setCode(role);
            roles.add(userRole);
            user.setRoles(roles);
            //user.setPassword(Encryption.generateStringPasswordHash(password2));
            user.setPassword(password2);
            try {
                dao.addUser(user);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User added successfully!", "INFO MSG"));
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error with adding a User!", "ERROR MSG"));
            }
        }
    }

    public boolean isPasswordsMatch() {
        return (user.getPassword().equals(password2) ? true : false);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDAO getDao() {
        return dao;
    }

    public void setDao(UserDAO dao) {
        this.dao = dao;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
