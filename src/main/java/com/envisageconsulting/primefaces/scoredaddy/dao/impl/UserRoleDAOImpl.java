package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.UserRoleDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@ManagedBean(name = "userRoleDAOImpl")
@SessionScoped
public class UserRoleDAOImpl implements UserRoleDAO {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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
}
