package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.AccountDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Account;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AccountDAOImpl implements AccountDAO {

    private DataSource dataSource;

    public void addAccount(Account account) throws Exception {
        String sql = "insert into account (name, street, city, state, zipcode, phone) values (?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account.getName());
            ps.setString(2, account.getAddress().getStreet());
            ps.setString(3, account.getAddress().getCity());
            ps.setString(4, account.getAddress().getState());
            ps.setString(5, account.getAddress().getZipcode());
            ps.setString(6, account.getPhone());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to add an Account!" + ex.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
