package com.envisageconsulting.primefaces.scoredaddy.dao;

public interface LoginDAO {

    public String getPasswordHash(String username) throws Exception;

}
