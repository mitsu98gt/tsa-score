package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;

import java.util.List;

public interface UserDAO {

    public List<UserRole> getAllUserRoles();

}
