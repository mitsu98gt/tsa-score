package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionCode;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionDetails;
import com.envisageconsulting.primefaces.scoredaddy.domain.UserRole;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDAOImpl implements CompetitionDAO {

    private DataSource dataSource;

    public List<CompetitionCode> getAllCompetitionCodes() {

        String sql = "select * from competition_codes";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<CompetitionCode> codes = new ArrayList();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CompetitionCode code = new CompetitionCode();
                code.setCompetitionCodeId(rs.getString("id"));
                code.setCode(rs.getString("code"));
                code.setDescription(rs.getString("description"));
                codes.add(code);
            }
            rs.close();
            ps.close();
            return codes;
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

    public void addCompetition(Competition competition) {

        String sql = "insert into competition (account_id, name, description) values (?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, competition.getAccountId());
            ps.setString(2, competition.getName());
            ps.setString(3, competition.getDescription());
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

    public void addCompetitionDetails(CompetitionDetails competitionDetails) {

        String sql = "insert into competition (id, code, date, course) values (?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, competitionDetails.getCompetitionDetailsId());
            ps.setString(2, competitionDetails.getCompetitionCode().getCode());
            ps.setString(3, DateUtils.getDateTime(competitionDetails.getDate()));
            ps.setString(4, competitionDetails.getCourse());
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
