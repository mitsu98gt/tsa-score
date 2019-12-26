package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompetitorDAOImpl implements CompetitorDAO {

    private DataSource dataSource;

    public List<Competitor> getAllCompetitors() throws Exception {

        String sql = "select id, first_name, last_name from competitor order by first_name";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<Competitor> competitorList = new ArrayList<Competitor>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competitor competitor =  new Competitor();
                competitor.setCompetitorId(Integer.toString(rs.getInt("id")));
                competitor.setFirstName(rs.getString("first_name"));
                competitor.setLastName(rs.getString("last_name"));
                competitorList.add(competitor);
            }
            rs.close();
            ps.close();
            return competitorList;
        } catch (SQLException ex) {
            throw new Exception("Failed to get all Competitors! " + ex.getMessage());
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

    public List<Competitor> getAllCompetitorsByAccountId(int accountId) throws Exception {
        String sql = "select id, first_name, last_name from competitor where id in (select competitor_id from competitor_account where account_id = ?) order by first_name";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<Competitor> competitorList = new ArrayList<Competitor>();

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competitor competitor =  new Competitor();
                competitor.setCompetitorId(Integer.toString(rs.getInt("id")));
                competitor.setFirstName(rs.getString("first_name"));
                competitor.setLastName(rs.getString("last_name"));
                competitorList.add(competitor);
            }
            rs.close();
            ps.close();
            return competitorList;
        } catch (SQLException ex) {
            throw new Exception("Failed to get all Competitors By AccountId! " + ex.getMessage());
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

    public List<Competitor> getCompetitorsByCompetitionId(int competitionId) throws Exception {

        String sql = "select id, first_name, last_name, gssf_id from competitor where id in (select competitor_id from competition_competitors where competition_id = ?) order by first_name";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<Competitor> competitorList = new ArrayList<Competitor>();

            ps.setInt(1, competitionId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competitor competitor =  new Competitor();
                competitor.setCompetitorId(Integer.toString(rs.getInt("id")));
                competitor.setFirstName(rs.getString("first_name"));
                competitor.setLastName(rs.getString("last_name"));
                competitor.setGssfId(rs.getString("gssf_id"));
                competitorList.add(competitor);
            }
            rs.close();
            ps.close();
            return competitorList;
        } catch (SQLException ex) {
            throw new Exception("Failed to get competitors by copetitionId! " + ex.getMessage());
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

    public int addCompetitor(Competitor competitor) throws Exception {

        String sql = "insert into competitor (first_name, last_name, street, city, state, zipcode, phone, email, gssf_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
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

            ResultSet rs = ps.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            ps.close();

            return generatedKey;

        } catch (SQLException ex) {
            throw new Exception("Failed to add Competitor! " + ex.getMessage());
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

    public void addCompetitorAccount(int competitorId, int accountId) throws Exception {

        String sql = "insert into competitor_account (competitor_id, account_id) values (?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, competitorId);
            ps.setInt(2, accountId);
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to add competitor to competitor_account! " + ex.getMessage());
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
