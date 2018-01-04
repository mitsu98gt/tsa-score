package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDAOImpl implements CompetitionDAO {

    private DataSource dataSource;

    public List<CompetitionCode> getAllCompetitionCodes() throws Exception {

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
        } catch (SQLException ex) {
            throw new Exception("Failed to get competition codes!" + ex.getMessage());
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

    public List<CourseCode> getAllCourseCodes() throws Exception {

        String sql = "select * from course_codes";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<CourseCode> codes = new ArrayList();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CourseCode code = new CourseCode();
                code.setId(rs.getString("id"));
                code.setCourse(rs.getString("course"));
                codes.add(code);
            }
            rs.close();
            ps.close();
            return codes;
        } catch (SQLException ex) {
            throw new Exception("Failed to get course codes!" + ex.getMessage());
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

    public List<Competition> getAllCompetitionsByAccountId(int accountId) throws Exception {

        String sql = "select id, name, description, status from competition where account_id = ?";

        Connection conn = null;

        try {
            List<Competition> competitions = new ArrayList();

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competition competition = new Competition();
                competition.setId(rs.getString("id"));
                competition.setName(rs.getString("name"));
                competition.setDescription(rs.getString("description"));
                competition.setStatus(rs.getString("status"));
                competitions.add(competition);
            }
            rs.close();
            ps.close();
            return competitions;
        } catch (SQLException ex) {
            throw new Exception("Failed to get all Competitions by AccountId!" + ex.getMessage());
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

    public List<Competition> getCompetitionsByAccountIdAndStatus(int accountId, String status) throws Exception {

        String sql = "select id, name, description from competition where account_id = ? and status = ?";

        Connection conn = null;

        try {
            List<Competition> competitions = new ArrayList();

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competition competition = new Competition();
                competition.setId(rs.getString("id"));
                competition.setName(rs.getString("name"));
                competition.setDescription(rs.getString("description"));
                competitions.add(competition);
            }
            rs.close();
            ps.close();
            return competitions;
        } catch (SQLException ex) {
            throw new Exception("Failed to get Competitions by AccountId and Status!" + ex.getMessage());
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

    public void addCompetition(Competition competition) throws Exception {

        String sql = "insert into competition (account_id, name, description) values (?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, competition.getAccountId());
            ps.setString(2, competition.getName());
            ps.setString(3, competition.getDescription());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to add Competition!" + ex.getMessage());
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

    public void addCompetitionDetails(CompetitionDetails competitionDetails) throws Exception {

        String sql = "insert into competition_details (id, code, date, course) values (?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, competitionDetails.getCompetitionDetailsId());
            ps.setString(2, competitionDetails.getCompetitionCode().getCompetitionCodeId());
            ps.setString(3, DateUtils.getDate(competitionDetails.getDate()));
            ps.setString(4, competitionDetails.getCourse());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to add Competition Details!" + ex.getMessage());
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

    public void addCompetitionCompetitors(int competitionId, int competitorId) throws Exception {

        String sql = "insert into competition_competitors (competition_id, competitor_id) values (?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, competitionId);
            ps.setInt(2, competitorId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Failed to add Competitors to Competition!" + ex.getMessage());
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

    public void updateCompetitionStatus(String status, int competitionId) throws Exception {

        String sql = "update competition set status = ? where id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, competitionId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            throw new Exception("Failed to update Competition status!" + ex.getMessage());
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
