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
                "     sc.description as status_description,\n" +
                "     (select count(*) from competition_results where id = c.id and additional_entry = false) as designated_entries,\n" +
                "     (select count(*) from competition_results where id = c.id and additional_entry = true) as additional_entries,\n" +
                "     (select concat(first_name, \" \", last_name) from competitor where id = \n" +
                "        (select competitor_id from competition_results where final_score = \n" +
                "        (select max(final_score) from competition_results where id=c.id and stock_division and additional_entry=false) \n" +
                "        and id=c.id and additional_entry = false and stock_division)\n" +
                "   ) as stock_leader,\n" +
                "     (select max(final_score) from competition_results where id=c.id and stock_division and additional_entry=false) as stock_score,\n" +
                "     (select concat(first_name, \" \", last_name) from competitor where id = \n" +
                "        (select competitor_id from competition_results where final_score = \n" +
                "        (select max(final_score) from competition_results where id=c.id and unlimited_division and additional_entry=false) \n" +
                "        and id=c.id and additional_entry = false and unlimited_division)\n" +
                "   ) as unlimited_leader,\n" +
                "     (select max(final_score) from competition_results where id=c.id and unlimited_division and additional_entry=false) as unlimited_score,\n" +
                "     (select concat(first_name, \" \", last_name) from competitor where id = \n" +
                "        (select competitor_id from competition_results where final_score = \n" +
                "        (select max(final_score) from competition_results where id=c.id and pocket_division and additional_entry=false) \n" +
                "        and id=c.id and additional_entry = false and pocket_division)\n" +
                "   ) as pocket_leader,\n" +
                "     (select max(final_score) from competition_results where id=c.id and pocket_division and additional_entry=false) as pocket_score,\n" +
                "     (select concat(first_name, \" \", last_name) from competitor where id = \n" +
                "        (select competitor_id from competition_results where final_score = \n" +
                "        (select max(final_score) from competition_results where id=c.id and rimfire_division and additional_entry=false) \n" +
                "        and id=c.id and additional_entry = false and rimfire_division)\n" +
                "   ) as rimfire_leader,\n" +
                "     (select max(final_score) from competition_results where id=c.id and rimfire_division and additional_entry=false) as rimfire_score,\n" +
                "     (select concat(first_name, \" \", last_name) from competitor where id = \n" +
                "        (select competitor_id from competition_results where final_score = \n" +
                "        (select max(final_score) from competition_results where id=c.id and woman_division and additional_entry=false) \n" +
                "        and id=c.id and additional_entry = false and woman_division)\n" +
                "   ) as woman_leader,\n" +
                "     (select max(final_score) from competition_results where id=c.id and woman_division and additional_entry=false) as woman_score,\n" +
                "     (select concat(first_name, \" \", last_name) from competitor where id = \n" +
                "        (select competitor_id from competition_results where final_score = \n" +
                "        (select max(final_score) from competition_results where id=c.id and senior_division and additional_entry=false) \n" +
                "        and id=c.id and additional_entry = false and senior_division)\n" +
                "   ) as senior_leader,\n" +
                "     (select max(final_score) from competition_results where id=c.id and senior_division and additional_entry=false) as senior_score,\n" +
                "     (select concat(first_name, \" \", last_name) from competitor where id = \n" +
                "        (select competitor_id from competition_results where final_score = \n" +
                "        (select max(final_score) from competition_results where id=c.id and junior_division and additional_entry=false) \n" +
                "        and id=c.id and additional_entry = false and junior_division)\n" +
                "   ) as junior_leader,\n" +
                "     (select max(final_score) from competition_results where id=c.id and junior_division and additional_entry=false) as junior_score\n" +
                "from \n" +
                "   tournament t,\n" +
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
                dashboardRow.setDesignatedEntries(rs.getString("designated_entries"));
                dashboardRow.setAdditionalEntries(rs.getString("additional_entries"));
                dashboardRow.setStockLeader(rs.getString("stock_leader"));
                dashboardRow.setStockScore(rs.getString("stock_score"));
                dashboardRow.setUnlimitedLeader(rs.getString("unlimited_leader"));
                dashboardRow.setUnlimitedScore(rs.getString("unlimited_score"));
                dashboardRow.setPocketLeader(rs.getString("pocket_leader"));
                dashboardRow.setPocketScore(rs.getString("pocket_score"));
                dashboardRow.setRimfireLeader(rs.getString("rimfire_leader"));
                dashboardRow.setRimfireScore(rs.getString("rimfire_score"));
                dashboardRow.setWomanLeader(rs.getString("woman_leader"));
                dashboardRow.setWomanScore(rs.getString("woman_score"));
                dashboardRow.setSeniorLeader(rs.getString("senior_leader"));
                dashboardRow.setSeniorScore(rs.getString("senior_score"));
                dashboardRow.setJuniorLeader(rs.getString("junior_leader"));
                dashboardRow.setJuniorScore(rs.getString("junior_score"));
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
