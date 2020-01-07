package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompetitionDAOImpl implements CompetitionDAO {

    private DataSource dataSource;

    public int getNumberOfCompetitionsInTournament(int tournamentId) throws Exception {

        String sql = "select count(*) as entries from competition where tournament_id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tournamentId);
            ResultSet rs = ps.executeQuery();

            int entries = 0;
            while (rs.next()) {
                entries = rs.getInt("entries");
            }
            rs.close();
            ps.close();

            return entries;

        } catch (SQLException ex) {
            throw new Exception("Failed to getNumberOfCompetitionsInTournament!" + ex);
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

        String sql = "select id, sequence, name, description, status from competition where account_id = ?";

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
                competition.setSequence(rs.getInt("sequence"));
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

    public List<Competition> getCompetitionsByTournamentIdAndStatus(int tournamentId, String status) throws Exception {

        //String sql = "select id, name, description from competition where account_id = ? and status = ?";
        String sql = "select c.id, c.name, c.description, cd.date, cd.code from competition c, competition_details cd where c.tournament_id = ? and c.status = ? and c.id = cd.id order by c.id";

        Connection conn = null;

        try {
            List<Competition> competitions = new ArrayList();

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, tournamentId);
            ps.setString(2, status.toUpperCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competition competition = new Competition();
                CompetitionDetails competitionDetails = new CompetitionDetails();
                CompetitionCode competitionCode = new CompetitionCode();

                competition.setId(rs.getString("id"));
                competition.setName(rs.getString("name"));
                competition.setDescription(rs.getString("description"));
                competition.setDate(rs.getDate("date"));
                competitionCode.setCode(rs.getString("code"));

                competitionDetails.setCompetitionCode(competitionCode);
                competition.setCompetitionDetails(competitionDetails);

                competitions.add(competition);
            }
            rs.close();
            ps.close();
            return competitions;
        } catch (SQLException ex) {
            throw new Exception("Failed to get Competitions by TournamentId and Status!" + ex.getMessage());
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

    public List<Competition> getAllCompetitionsByAccountIdAndStatus(int accountId, String status) throws Exception {

        String sql = "select c.id, c.sequence, c.name, c.description, cd.date, cd.code from competition c, competition_details cd where c.account_id = ? and c.status = ? and c.id = cd.id order by c.id";

        Connection conn = null;

        try {
            List<Competition> competitions = new ArrayList();

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, status.toUpperCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competition competition = new Competition();
                CompetitionDetails competitionDetails = new CompetitionDetails();
                CompetitionCode competitionCode = new CompetitionCode();

                competition.setId(rs.getString("id"));
                competition.setSequence(rs.getInt("sequence"));
                competition.setName(rs.getString("name"));
                competition.setDescription(rs.getString("description"));
                competition.setDate(rs.getDate("date"));
                competitionCode.setCode(rs.getString("code"));

                competitionDetails.setCompetitionCode(competitionCode);
                competition.setCompetitionDetails(competitionDetails);

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

    public List<Competition> getGlockCompetitionsByAccountIdAndStatus(int accountId, String status) throws Exception {

        String sql = "select c.id, c.name, c.sequence, c.description, cd.date, cd.code from competition c, competition_details cd where c.account_id = ? and c.status = ? and c.id = cd.id and cd.code = 1 order by c.id";

        Connection conn = null;

        try {
            List<Competition> competitions = new ArrayList();

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, status.toUpperCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competition competition = new Competition();
                CompetitionDetails competitionDetails = new CompetitionDetails();
                CompetitionCode competitionCode = new CompetitionCode();

                competition.setId(rs.getString("id"));
                competition.setName(rs.getString("name"));
                competition.setSequence(rs.getInt("sequence"));
                competition.setDescription(rs.getString("description"));
                competition.setDate(rs.getDate("date"));
                competitionCode.setCode(rs.getString("code"));

                competitionDetails.setCompetitionCode(competitionCode);
                competition.setCompetitionDetails(competitionDetails);

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

    public List<Competition> getBullseyeCompetitionsByAccountIdAndStatus(int accountId, String status) throws Exception {

        String sql = "select c.id, c.name, c.description, cd.date, cd.code from competition c, competition_details cd where c.account_id = ? and c.status = ? and c.id = cd.id and cd.code = 2 order by c.id";

        Connection conn = null;

        try {
            List<Competition> competitions = new ArrayList();

            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accountId);
            ps.setString(2, status.toUpperCase());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Competition competition = new Competition();
                CompetitionDetails competitionDetails = new CompetitionDetails();
                CompetitionCode competitionCode = new CompetitionCode();

                competition.setId(rs.getString("id"));
                competition.setName(rs.getString("name"));
                competition.setDescription(rs.getString("description"));
                competition.setDate(rs.getDate("date"));
                competitionCode.setCode(rs.getString("code"));

                competitionDetails.setCompetitionCode(competitionCode);
                competition.setCompetitionDetails(competitionDetails);

                competitions.add(competition);
            }
            rs.close();
            ps.close();
            return competitions;
        } catch (SQLException ex) {
            throw new Exception("Failed to get Bullseye Competitions by AccountId and Status!" + ex.getMessage());
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

    public int addCompetition(Competition competition) throws Exception {

        String sql = "insert into competition (tournament_id, account_id, name, description, sequence) values (?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, competition.getTournament_id());
            ps.setInt(2, competition.getAccountId());
            ps.setString(3, competition.getName());
            ps.setString(4, competition.getDescription());
            ps.setInt(5, competition.getSequence());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int generatedKey = 0;
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }

            ps.close();
            return generatedKey;

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

    public List<String> getDivisionCodesByCompetitionCode(int competitionCode) throws Exception {

        String sql = "select code from division_codes where id = ? and code != 'GSSF_WOMAN' and code != 'GSSF_SENIOR' and code != 'GSSF_JUNIOR' order by code";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, competitionCode);
            List<String> codes = new ArrayList();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                codes.add(rs.getString("code"));
            }
            rs.close();
            ps.close();
            return codes;
        } catch (SQLException ex) {
            throw new Exception("Failed to get division codes by Competition Code!" + ex.getMessage());
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
