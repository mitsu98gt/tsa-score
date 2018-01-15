package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.TournamentDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.CourseCode;
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

    public List<Tournament> getAllTournamentsByAccountIdAndStatus(int accountId, String status) throws Exception {

        String sql = "select * from tournament where account_id = ? and status = ?";

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
