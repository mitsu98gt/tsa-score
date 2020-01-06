package com.envisageconsulting.primefaces.scoredaddy.dao;

import com.envisageconsulting.primefaces.scoredaddy.domain.Account;

public interface LoginDAO {

    public String getPasswordHash(String username) throws Exception;
    public Account getUserAccount(String username) throws Exception;
    public void updateLastLogin(String username, int accountId) throws  Exception;
}
