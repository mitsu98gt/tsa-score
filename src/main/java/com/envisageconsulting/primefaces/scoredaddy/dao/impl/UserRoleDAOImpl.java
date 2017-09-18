package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.UserRoleDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRoleDAOImpl implements UserRoleDAO {

    private DataSource dataSource;

    public List<UserRole> getAllUserRoles() {

        String sql = "select * from role_codes";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<UserRole> userRoles = null;

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userRoles.add(new UserRole(
                        rs.getString("code"),
                        rs.getString("description")
                ));
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

    public String getMessage() {
        return "Hello Vinh!";
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
