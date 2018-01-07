package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
import com.envisageconsulting.primefaces.scoredaddy.SQLConstants;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.TargetOne;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.TargetTwo;

import javax.sql.DataSource;
import java.lang.annotation.Target;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CompetitionResultsDAOImpl implements CompetitionResultsDAO {

    private DataSource dataSource;

    public List<CompetitionResults> getCompetitionResultsByDivisionAndCompetitionId(String division, int competitionId) throws Exception {

        List<CompetitionResults> competitionResultsList = new ArrayList<CompetitionResults>();

        String sql = String.format(SQLConstants.COMPETITION_RESULTS_QUERY_BY_DIVISION_AND_COMPETITION_ID, division);

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, competitionId);

            ResultSet rs = ps.executeQuery();
            int rank = 0;
            while (rs.next()) {

                CompetitionResults competitionResults = new CompetitionResults();
                CompetitionDetails competitionDetails = new CompetitionDetails();

                competitionDetails.setDate(rs.getDate("date"));
                competitionResults.setCompetitionDetails(competitionDetails);

                GSSFIndoorScoreSheet gssfIndoorScoreSheet = new GSSFIndoorScoreSheet();

                Competitor competitor = new Competitor();
                competitor.setFirstName(rs.getString("first_name"));
                competitor.setLastName(rs.getString("last_name"));
                gssfIndoorScoreSheet.setCompetitor(competitor);

                Firearm firearm = new Firearm();
                firearm.setModel(rs.getString("model"));
                gssfIndoorScoreSheet.setFirearm(firearm);

                TargetOne targetOne = new TargetOne();
                targetOne.setX(rs.getInt("target_one_x"));
                targetOne.setTen(rs.getInt("target_one_ten"));
                targetOne.setEight(rs.getInt("target_one_eight"));
                targetOne.setFive(rs.getInt("target_one_five"));
                targetOne.setMisses(rs.getInt("target_one_misses"));
                gssfIndoorScoreSheet.setTargetOne(targetOne);

                TargetTwo targetTwo = new TargetTwo();
                targetTwo.setX(rs.getInt("target_two_x"));
                targetTwo.setTen(rs.getInt("target_two_ten"));
                targetTwo.setEight(rs.getInt("target_two_eight"));
                targetTwo.setFive(rs.getInt("target_two_five"));
                targetTwo.setMisses(rs.getInt("target_two_misses"));
                gssfIndoorScoreSheet.setTargetTwo(targetTwo);

                gssfIndoorScoreSheet.setPenalty(rs.getInt("penalty"));
                gssfIndoorScoreSheet.setFinalScore(rs.getInt("final_score"));
                gssfIndoorScoreSheet.setTotalX(rs.getInt("total_x"));

                competitionResults.setGssfIndoorScoreSheet(gssfIndoorScoreSheet);
                competitionResults.setRank(rank + 1);
                rank++;

                Competition competition = new Competition();
                competition.setDescription(rs.getString("description"));
                competitionResults.setCompetition(competition);

                Account account = new Account();
                account.setName(rs.getString("name"));
                competitionResults.setAccount(account);

                competitionResultsList.add(competitionResults);

            }

            rs.close();
            ps.close();

            return competitionResultsList;

        } catch (SQLException ex) {
            throw new Exception(ex);
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

    public List<Integer> getCompetitorIdByCompetitionAndDivision(int competition_id, String division) throws Exception {

        List<Integer> competitorIdList = new ArrayList<Integer>();

        String sql = "select distinct(competitor_id) from competition_results where id = ? and stock_division = ? order by competitor_id";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, competition_id);
            ps.setString(2, division);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                competitorIdList.add(rs.getInt("competitor_id"));
            }

            rs.close();
            ps.close();

            return competitorIdList;

        } catch (SQLException ex) {
            throw new Exception("Failed to get competitor Ids! " + ex);
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

    public List<Integer> getCompetitorFirearmByCompetitionAndDivision(int competitor_id, int competition_id, String division) throws Exception {

        List<Integer> competitorFirearmList = new ArrayList<Integer>();

        String sql = "select firearm_id from competition_results where competitor_id = ? and id = ? and stock_division = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, competitor_id);
            ps.setInt(2, competition_id);
            ps.setString(3, division);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                competitorFirearmList.add(rs.getInt("firearm_id"));
            }

            rs.close();
            ps.close();

            return competitorFirearmList;

        } catch (SQLException ex) {
            throw new Exception("Failed to get competitor and firearm from competition results! " + ex);
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

    public CompetitionResultsRow getCompetitionResultsByCompetitionCompetitorFirearmDivision(int competition_id, int competitor_id, int firearm_id, String division) throws Exception {

        CompetitionResultsRow competitionResultsRow = new CompetitionResultsRow();

        String sql = String.format(SQLConstants.COMPETITION_RESULTS_QUERY_BY_COMPETITON_COMPETITOR_FIREARM_DIVISION, division);

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, competition_id);
            ps.setInt(2, competitor_id);
            ps.setInt(3, firearm_id);
            ps.setString(4, division);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

               competitionResultsRow.setFirst_name(rs.getString("first_name"));
               competitionResultsRow.setLast_name(rs.getString("last_name"));
               competitionResultsRow.setFirearm_model(rs.getString("firearm_model"));
               competitionResultsRow.setDate(rs.getDate("date"));
               competitionResultsRow.setTotal_x(rs.getString("total_x"));
               competitionResultsRow.setTotal_ten(rs.getString("total_ten"));
               competitionResultsRow.setTotal_eight(rs.getString("total_ten"));
               competitionResultsRow.setTotal_five(rs.getString("total_five"));
               competitionResultsRow.setTotal_misses(rs.getString("total_misses"));
               competitionResultsRow.setPenalty(rs.getString("penalty"));
               competitionResultsRow.setFinal_score(rs.getString("final_score"));

            }

            rs.close();
            ps.close();

            return competitionResultsRow;

        } catch (SQLException ex) {
            throw new Exception("Failed to get competion results by competition, competitor, firearm, and divsion! " + ex);
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

    public void addCompetitionResults(CompetitionResults competitionResults) throws Exception {

        String sql = "insert into competition_results (id, code, competition_results.date, competitor_id, firearm_id, stock_division, unlimited_division, " +
                "pocket_division, woman_division, senior_division, junior_division, " +
                "target_one_x, target_one_ten, target_one_eight, target_one_five, target_one_misses, target_two_x, " +
                "target_two_ten, target_two_eight, target_two_five, target_two_misses, penalty, final_score, total_x, " +
                "range_officer_initials, competitor_initials) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(competitionResults.getCompetitionDetails().getCompetitionDetailsId()));
            ps.setInt(2, Integer.valueOf(competitionResults.getCompetitionDetails().getCompetitionCode().getCode()));
            //java.util.Date utilDate = new SimpleDateFormat("MM-dd-yyyy").parse(DateUtils.getDate(competitionResults.getCompetitionDetails().getDate()));
            ps.setString(3, DateUtils.getDate(competitionResults.getCompetitionDetails().getDate()));
            ps.setInt(4, Integer.valueOf(competitionResults.getCompetitionCompetitors().getCompetitorId()));
            ps.setInt(5, Integer.valueOf(competitionResults.getCompetitionCompetitors().getFirearmId()));
            ps.setBoolean(6, competitionResults.getGssfIndoorScoreSheet().getDivsion().isStock());
            ps.setBoolean(7, competitionResults.getGssfIndoorScoreSheet().getDivsion().isUnlimited());
            ps.setBoolean(8, competitionResults.getGssfIndoorScoreSheet().getDivsion().isPocket());
            ps.setBoolean(9, competitionResults.getGssfIndoorScoreSheet().getDivsion().isWoman());
            ps.setBoolean(10, competitionResults.getGssfIndoorScoreSheet().getDivsion().isSenior());
            ps.setBoolean(11, competitionResults.getGssfIndoorScoreSheet().getDivsion().isJunior());
            ps.setInt(12, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getX());
            ps.setInt(13, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getTen());
            ps.setInt(14, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getEight());
            ps.setInt(15, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getFive());
            ps.setInt(16, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getMisses());
            ps.setInt(17, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getX());
            ps.setInt(18, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getTen());
            ps.setInt(19, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getEight());
            ps.setInt(20, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getFive());
            ps.setInt(21, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getMisses());
            ps.setInt(22, competitionResults.getGssfIndoorScoreSheet().getPenalty());
            ps.setInt(23, competitionResults.getGssfIndoorScoreSheet().getFinalScore());
            ps.setInt(24, competitionResults.getGssfIndoorScoreSheet().getTotalX());
            ps.setString(25, competitionResults.getGssfIndoorScoreSheet().getRangeOfficerInitials().toUpperCase());
            ps.setString(26, competitionResults.getGssfIndoorScoreSheet().getCompetitorInitials().toUpperCase());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to add Competition Results!" + ex.getMessage());
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
