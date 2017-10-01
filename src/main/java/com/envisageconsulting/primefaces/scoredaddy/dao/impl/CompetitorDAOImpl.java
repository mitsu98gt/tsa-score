package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompetitorDAOImpl implements CompetitorDAO {

    private DataSource dataSource;

    public void addCompetitor(Competitor competitor) {
        String sql = "insert into users (first_name, last_name, street, city, state, zipcode, phone, email, gssf_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, competitor.getFirstName());
            ps.setString(2, competitor.getLastName());
            ps.setString(3, competitor.getAddress().getStreet());
            ps.setString(4, competitor.getAddress().getCity());
            ps.setString(5, competitor.getAddress().getState());
            ps.setString(6, competitor.getAddress().getZipcode());
            ps.setString(7, competitor.getPhone());
            ps.setString(8, competitor.getEmail());
            ps.setString(9, competitor.getGssfId());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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
