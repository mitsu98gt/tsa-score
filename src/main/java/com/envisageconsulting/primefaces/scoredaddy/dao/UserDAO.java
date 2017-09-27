package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.User;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.util.List;

public interface UserDAO {

    public List<UserRole> getAllUserRoles();
    public void addUser(User user) throws MySQLIntegrityConstraintViolationException;
}
