package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.LoginDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;

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

        String sql = "select u.account_id, a.name, a.street, a.city, a.state, a.zipcode, a.phone, u.first_name, u.last_name, u.role_code, u.email from users u, account a where username = ? and u.account_id = a.id";

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
                String phone = rs.getString("phone");
                //012-345-6789
                //123-456-7890
                String formattedPhone = "(" + phone.substring(0,3) + ") " + phone.substring(3,6) + "-" + phone.substring(6,10);
                account.setPhone(formattedPhone);
                address.setStreet(rs.getString("street"));
                address.setCity(rs.getString("city"));
                address.setState(rs.getString("state"));
                address.setZipcode(rs.getString("zipcode"));
                account.setAddress(address);

                User user = new User();
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                UserRole userRole = new UserRole();
                userRole.setCode(rs.getString("role_code"));
                List<UserRole> roles = new ArrayList<>();
                roles.add(userRole);
                user.setRoles(roles);

                account.setUser(user);
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

    public void updateLastLogin(String username, int accountId) throws  Exception {

        String sql = "update users set last_login = CURRENT_TIMESTAMP where username = ? and account_id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setInt(2, accountId);
            ps.executeUpdate();

            ps.close();

        } catch (SQLException ex) {
            throw new Exception("Failed to update Last Login!" + ex.getMessage());
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
