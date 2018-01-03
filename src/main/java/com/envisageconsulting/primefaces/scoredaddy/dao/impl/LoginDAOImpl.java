package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.LoginDAO;
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
                } catch (SQLException e) {}
            }
        }
    }

    public int getUserAccountId(String username) throws Exception {

        String sql = "select account_id from users where username = ?";

        int accountId = 0;

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                accountId = rs.getInt("account_id");
            }

            rs.close();
            ps.close();
            return accountId;
        } catch (SQLException e) {
            throw new Exception(e);
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
