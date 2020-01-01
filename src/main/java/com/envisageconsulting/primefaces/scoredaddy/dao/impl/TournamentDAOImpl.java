package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.TournamentDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.CourseCode;
import com.envisageconsulting.primefaces.scoredaddy.domain.DashboardRow;
import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TournamentDAOImpl implements TournamentDAO {

    private DataSource dataSource;

    public DataSource getDataSource() {
        return dataSource;
    }

    public List<DashboardRow> getDashboardRows(int accountId) throws Exception {

        String sql = "select \n" +
                "     t.name as tournament_name,\n" +
                "     c.name as competition_name,\n" +
                "     cd.date as competition_date,\n" +
                "     sc.description as status_description\n" +
                "from \n" +
                "\t tournament t,\n" +
                "     competition c,\n" +
                "     competition_details cd,\n" +
                "     status_codes sc\n" +
                "where \n" +
                "     t.id = c.tournament_id\n" +
                "and  c.id = cd.id\n" +
                "and  t.account_id = c.account_id\n" +
                "and  c.status = sc.code\n" +
                "and  t.account_id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);

            List<DashboardRow> dashboardRows = new ArrayList();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                DashboardRow dashboardRow = new DashboardRow();
                dashboardRow.setTournamentName(rs.getString("tournament_name"));
                dashboardRow.setCompetitionName(rs.getString("competition_name"));
                dashboardRow.setCompetitionDate(DateUtils.getDate(rs.getDate("competition_date")));
                dashboardRow.setCompetitionStatusDescription(rs.getString("status_description"));
                dashboardRows.add(dashboardRow);
            }

            rs.close();
            ps.close();
            return dashboardRows;

        } catch (SQLException ex) {
            throw new Exception("Failed to get tournaments by accountId and Status!" + ex.getMessage());
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
    public List<Tournament> getAllTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception {

        String sql = "select * from tournament where account_id = ? and status = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, status);

            List<Tournament> tournamentList = new ArrayList();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tournament tournament = new Tournament();
                tournament.setId(rs.getInt("id"));
                tournament.setAccount_id(rs.getInt("account_id"));
                tournament.setName(rs.getString("name"));
                tournament.setDescription(rs.getString("description"));
                tournament.setStatus(rs.getString("status"));
                tournamentList.add(tournament);
            }

            rs.close();
            ps.close();
            return tournamentList;

        } catch (SQLException ex) {
            throw new Exception("Failed to get tournaments by accountId and Status!" + ex.getMessage());
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

    public List<Tournament> getAllGlockTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception {

        String sql = "select id, account_id, name, description, status from tournament where " +
                "id in (select c.tournament_id from competition c, competition_details cd where " +
                "c.id = cd.id and cd.code = 1) and account_id = ? and status = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            List<Tournament> tournamentList = new ArrayList();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, status);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tournament tournament = new Tournament();
                tournament.setId(rs.getInt("id"));
                tournament.setAccount_id(rs.getInt("account_id"));
                tournament.setName(rs.getString("name"));
                tournament.setDescription(rs.getString("description"));
                tournament.setStatus(rs.getString("status"));
                tournamentList.add(tournament);
            }

            rs.close();
            ps.close();
            return tournamentList;

        } catch (SQLException ex) {
            throw new Exception("Failed to get Glock tournaments by accountId and Status!" + ex.getMessage());
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

    public List<Tournament> getAllBullseyeTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception {

        String sql = "select id, account_id, name, description, status from tournament where " +
                "id in (select c.tournament_id from competition c, competition_details cd where " +
                "c.id = cd.id and cd.code = 2) and account_id = ? and status = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            List<Tournament> tournamentList = new ArrayList();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, status);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tournament tournament = new Tournament();
                tournament.setId(rs.getInt("id"));
                tournament.setAccount_id(rs.getInt("account_id"));
                tournament.setName(rs.getString("name"));
                tournament.setDescription(rs.getString("description"));
                tournament.setStatus(rs.getString("status"));
                tournamentList.add(tournament);
            }

            rs.close();
            ps.close();
            return tournamentList;

        } catch (SQLException ex) {
            throw new Exception("Failed to get bullseye tournaments by accountId and Status!" + ex.getMessage());
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

    public void addTournament(Tournament tournament) throws  Exception {

        String sql = "insert into tournament (account_id, name, description) values (?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tournament.getAccount_id());
            ps.setString(2, tournament.getName());
            ps.setString(3, tournament.getDescription());
            ps.executeUpdate();

            ps.close();

        } catch (SQLException ex) {
            throw new Exception("Failed to add Tournament!" + ex.getMessage());
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
