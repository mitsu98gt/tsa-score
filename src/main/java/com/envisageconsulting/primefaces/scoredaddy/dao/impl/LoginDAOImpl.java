package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.LoginDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Account;
import com.envisageconsulting.primefaces.scoredaddy.domain.Address;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginDAOImpl implements LoginDAO {

    private DataSource dataSource;

    public String getPasswordHash(String username) throws Exception {

        String sql = "select password from users where username = ?";
        String passwordHash = "";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
               passwordHash = rs.getString("password");
            }

            rs.close();
            ps.close();
            return passwordHash;
        } catch (SQLException e) {
            throw new Exception(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Account getUserAccount(String username) throws Exception {

        Account account = new Account();

        String sql = "select u.account_id, a.name, a.street, a.city, a.state, a.zipcode, a.phone from users u, account a where username = ? and u.account_id = a.id";

        int accountId = 0;

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Address address = new Address();
                account.setId(rs.getString("account_id"));
                account.setName(rs.getString("name"));
                address.setStreet(rs.getString("street"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                address.setZipcode(rs.getString("zipcode"));
                account.setAddress(address);
            }

            rs.close();
            ps.close();
            return account;
        } catch (SQLException e) {
            throw new Exception(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
