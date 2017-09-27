package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.UserDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.User;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private DataSource dataSource;

    public List<UserRole> getAllUserRoles() {

        String sql = "select * from role_codes";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<UserRole> userRoles = new ArrayList();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserRole urole = new UserRole();
                urole.setCode(rs.getString("code"));
                urole.setDescription(rs.getString("description"));
                userRoles.add(urole);
            }
            rs.close();
            ps.close();
            return userRoles;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public void addUser(User user) throws MySQLIntegrityConstraintViolationException {
        String sql = "insert into users (first_name, last_name, username, password, email, role_code, create_date) values (?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getEmail());
            ps.setString(6, user.getRoles().get(0).getCode());
            ps.setString(7, getDateTime());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new MySQLIntegrityConstraintViolationException();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }

    public String getDateTime() {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
