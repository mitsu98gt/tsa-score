package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
import com.envisageconsulting.primefaces.scoredaddy.SQLConstants;
import com.envisageconsulting.primefaces.scoredaddy.ScoreSheetUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.Division;
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

    public int getCompetitorNumberOfDesignatedEntriesByCometitionAndDivision(int competitionId, int competitorId, String division)  throws  Exception {

        String sql = "select count(*) as entries from competition_results where id = ? and competitor_id = ? and %s = true and additional_entry = false";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement(String.format(sql, division));
            ps.setInt(1, competitionId);
            ps.setInt(2, competitorId);
            ResultSet rs = ps.executeQuery();

            int entries = 0;
            while (rs.next()) {
                entries = rs.getInt("entries");
            }
            rs.close();
            ps.close();
            return entries;
        } catch (SQLException ex) {
            throw new Exception("Failed to getCompetitorNumberOfDesignatedEntriesByCometitionAndDivision!" + ex);
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

    public int getCompetitorNumberOfDesignatedEntriesByTournamentAndDivision(int competitionId, int competitorId, String division)  throws  Exception {

        String sql = "select count(*) as entries \n" +
                "  from competition_results \n" +
                "where id in (select id from competition where tournament_id = \n" +
                "  (select tournament_id from competition where id = ?))\n" +
                "and competitor_id = ?\n" +
                "and %s = true\n" +
                "and additional_entry = false";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement(String.format(sql, division));
            ps.setInt(1, competitionId);
            ps.setInt(2, competitorId);
            ResultSet rs = ps.executeQuery();

            int entries = 0;
            while (rs.next()) {
                entries = rs.getInt("entries");
            }
            rs.close();
            ps.close();

            return entries;

        } catch (SQLException ex) {
            throw new Exception("Failed to getCompetitorNumberOfDesignatedEntriesByCometitionAndDivision!" + ex);
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

    public List<Firearm> getCompetitorAdditionalEntriesByFirearms(int competitionId, int competitorId, String division)  throws  Exception {

        String sql = "select firearm_id from competition_results where id = ? and competitor_id = ? and %s = true and additional_entry = true";

        Connection conn = null;

        List<Firearm> firearms = new ArrayList<>();

        try {
            conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement(String.format(sql, division));
            ps.setInt(1, competitionId);
            ps.setInt(2, competitorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Firearm firearm = new Firearm();
                firearm.setId(String.valueOf(rs.getInt("firearm_id")));
                firearms.add(firearm);
            }
            rs.close();
            ps.close();

            return firearms;

        } catch (SQLException ex) {
            throw new Exception("Failed to getCompetitorNumberOfAdditionalEntriesByCometitionAndDivision!" + ex);
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

    public CompetitionResults getCompetitionResultsByCompetitionResultsId(int competitionResultsId, String division) throws Exception {

        String sql = String.format(SQLConstants.COMPETITION_RESULTS_QUERY_BY_COMPETITION_RESULTS_ID, division);

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, competitionResultsId);

            ResultSet rs = ps.executeQuery();
            int rank = 0;
            CompetitionResults competitionResults = new CompetitionResults();

            while (rs.next()) {

                CompetitionDetails competitionDetails = new CompetitionDetails();
                competitionDetails.setCompetitionDetailsId(String.valueOf(rs.getInt("id")));

                CompetitionCompetitors competitionCompetitors = new CompetitionCompetitors();
                competitionCompetitors.setCompetitorId(String.valueOf(rs.getInt("competitor_id")));
                competitionCompetitors.setFirearmId(String.valueOf(rs.getInt("firearm_id")));
                competitionResults.setCompetitionCompetitors(competitionCompetitors);

                competitionResults.setCompetitionResultsId(rs.getInt("competition_results_id"));
                competitionDetails.setDate(rs.getDate("date"));

                CompetitionCode competitionCode = new CompetitionCode();
                competitionCode.setCompetitionCodeId(String.valueOf(rs.getInt("code")));
                competitionDetails.setCompetitionCode(competitionCode);

                competitionResults.setCompetitionDetails(competitionDetails);

                GSSFIndoorScoreSheet gssfIndoorScoreSheet = new GSSFIndoorScoreSheet();

                Division div = new Division();
                div.setStock(rs.getBoolean("stock_division"));
                div.setUnlimited(rs.getBoolean("unlimited_division"));
                div.setPocket(rs.getBoolean("pocket_division"));
                div.setWoman(rs.getBoolean("woman_division"));
                div.setJunior(rs.getBoolean("junior_division"));
                div.setSenior(rs.getBoolean("senior_division"));
                div.setLimited(rs.getBoolean("limited_division"));
                div.setRevolver(rs.getBoolean("revolver_division"));
                div.setRimfire(rs.getBoolean("rimfire_division"));
                gssfIndoorScoreSheet.setDivsion(div);

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

                gssfIndoorScoreSheet.setRangeOfficerInitials(rs.getString("range_officer_initials"));
                gssfIndoorScoreSheet.setCompetitorInitials(rs.getString("competitor_initials"));

                gssfIndoorScoreSheet.setAdditionalEntry(rs.getBoolean("additional_entry"));

                competitionResults.setGssfIndoorScoreSheet(gssfIndoorScoreSheet);
                competitionResults.setRank(String.valueOf(rank + 1));
                rank++;

                Competition competition = new Competition();
                competition.setDescription(rs.getString("description"));
                competitionResults.setCompetition(competition);

                Account account = new Account();
                account.setName(rs.getString("name"));
                competitionResults.setAccount(account);

            }

            rs.close();
            ps.close();

            return competitionResults;

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

    public List<CompetitionResults> getCompetitionResultsByDivisionAndCompetitionId(String division, int competitionId, String additionalEntries) throws Exception {

        List<CompetitionResults> competitionResultsList = new ArrayList<CompetitionResults>();

        String sql = String.format(SQLConstants.COMPETITION_RESULTS_QUERY_BY_DIVISION_AND_COMPETITION_ID_AND_ADDITIONAL_ENTRIES, division, additionalEntries);

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
                competitionDetails.setCompetitionDetailsId(String.valueOf(rs.getInt("id")));

                CompetitionCompetitors competitionCompetitors = new CompetitionCompetitors();
                competitionCompetitors.setCompetitorId(String.valueOf(rs.getInt("competitor_id")));
                competitionCompetitors.setFirearmId(String.valueOf(rs.getInt("firearm_id")));
                competitionResults.setCompetitionCompetitors(competitionCompetitors);

                competitionResults.setCompetitionResultsId(rs.getInt("competition_results_id"));
                competitionDetails.setDate(rs.getDate("date"));

                CompetitionCode competitionCode = new CompetitionCode();
                competitionCode.setCompetitionCodeId(String.valueOf(rs.getInt("code")));
                competitionDetails.setCompetitionCode(competitionCode);

                competitionResults.setCompetitionDetails(competitionDetails);

                GSSFIndoorScoreSheet gssfIndoorScoreSheet = new GSSFIndoorScoreSheet();

                Division div = new Division();
                div.setStock(rs.getBoolean("stock_division"));
                div.setUnlimited(rs.getBoolean("unlimited_division"));
                div.setPocket(rs.getBoolean("pocket_division"));
                div.setWoman(rs.getBoolean("woman_division"));
                div.setJunior(rs.getBoolean("junior_division"));
                div.setSenior(rs.getBoolean("senior_division"));
                div.setLimited(rs.getBoolean("limited_division"));
                div.setRevolver(rs.getBoolean("revolver_division"));
                div.setRimfire(rs.getBoolean("rimfire_division"));
                gssfIndoorScoreSheet.setDivsion(div);
                
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

                gssfIndoorScoreSheet.setRangeOfficerInitials(rs.getString("range_officer_initials"));
                gssfIndoorScoreSheet.setCompetitorInitials(rs.getString("competitor_initials"));

                gssfIndoorScoreSheet.setAdditionalEntry(rs.getBoolean("additional_entry"));

                competitionResults.setGssfIndoorScoreSheet(gssfIndoorScoreSheet);
                competitionResults.setRank(String.valueOf(rank + 1));
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

    public List<CompetitionResults> getCompetitionResultsForUpdate(String division, int competitionId) throws Exception {

        List<CompetitionResults> competitionResultsList = new ArrayList<CompetitionResults>();

        String sql = String.format(SQLConstants.COMPETITION_RESULTS_FOR_UPDATE, division);

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
                competitionDetails.setCompetitionDetailsId(String.valueOf(rs.getInt("id")));

                CompetitionCompetitors competitionCompetitors = new CompetitionCompetitors();
                competitionCompetitors.setCompetitorId(String.valueOf(rs.getInt("competitor_id")));
                competitionCompetitors.setFirearmId(String.valueOf(rs.getInt("firearm_id")));
                competitionResults.setCompetitionCompetitors(competitionCompetitors);

                competitionResults.setCompetitionResultsId(rs.getInt("competition_results_id"));
                competitionDetails.setDate(rs.getDate("date"));

                CompetitionCode competitionCode = new CompetitionCode();
                competitionCode.setCompetitionCodeId(String.valueOf(rs.getInt("code")));
                competitionDetails.setCompetitionCode(competitionCode);

                competitionResults.setCompetitionDetails(competitionDetails);

                GSSFIndoorScoreSheet gssfIndoorScoreSheet = new GSSFIndoorScoreSheet();

                Division div = new Division();
                div.setStock(rs.getBoolean("stock_division"));
                div.setUnlimited(rs.getBoolean("unlimited_division"));
                div.setPocket(rs.getBoolean("pocket_division"));
                div.setWoman(rs.getBoolean("woman_division"));
                div.setJunior(rs.getBoolean("junior_division"));
                div.setSenior(rs.getBoolean("senior_division"));
                div.setLimited(rs.getBoolean("limited_division"));
                div.setRevolver(rs.getBoolean("revolver_division"));
                div.setRimfire(rs.getBoolean("rimfire_division"));
                gssfIndoorScoreSheet.setDivsion(div);

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

                gssfIndoorScoreSheet.setRangeOfficerInitials(rs.getString("range_officer_initials"));
                gssfIndoorScoreSheet.setCompetitorInitials(rs.getString("competitor_initials"));

                gssfIndoorScoreSheet.setAdditionalEntry(rs.getBoolean("additional_entry"));
                if (gssfIndoorScoreSheet.isAdditionalEntry()) {
                    competitionResults.setClassification("+Entry");
                } else {
                    competitionResults.setClassification("D");
                }

                competitionResults.setGssfIndoorScoreSheet(gssfIndoorScoreSheet);
                competitionResults.setRank(String.valueOf(rank + 1));
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

    public List<Competitor> getCompetitorIdByCompetitionAndDivision(int competition_id, String division, String additionalEntries) throws Exception {

        List<Competitor> competitorIdList = new ArrayList<Competitor>();

        String sql = "select distinct(competitor_id) from competition_results cr where id = ? and %s and additional_entry = %s order by competitor_id";
        String formattedSql = String.format(sql, division, additionalEntries);

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(formattedSql);

            ps.setInt(1, competition_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Competitor competitor =  new Competitor();
                competitor.setCompetitorId(rs.getString("competitor_id"));
                competitorIdList.add(competitor);
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

    public List<Firearm> getCompetitorFirearmByCompetitionAndDivision(int competitor_id, int competition_id, String division, String additionalEntries) throws Exception {

        List<Firearm> competitorFirearmList = new ArrayList<Firearm>();

        String sql = "select firearm_id from competition_results cr where competitor_id = ? and id = ? and %s and additional_entry = %s";
        String formattedSql = String.format(sql, division, additionalEntries);

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(formattedSql);

            ps.setInt(1, competitor_id);
            ps.setInt(2, competition_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Firearm firearm = new Firearm();
                firearm.setId(rs.getString("firearm_id"));
                competitorFirearmList.add(firearm);
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

    public CompetitionResultsRow getCompetitionResultsByCompetitionCompetitorFirearmDivision(int competition_id, int competitor_id, int firearm_id, String division, String additionalEntries) throws Exception {

        CompetitionResultsRow competitionResultsRow = new CompetitionResultsRow();

        String sql = String.format(SQLConstants.COMPETITION_RESULTS_QUERY_BY_COMPETITON_COMPETITOR_FIREARM_DIVISION, division, additionalEntries);

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, competition_id);
            ps.setInt(2, competitor_id);
            ps.setInt(3, firearm_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

               competitionResultsRow.setFirst_name(rs.getString("first_name"));
               competitionResultsRow.setLast_name(rs.getString("last_name"));
               competitionResultsRow.setFirearm_model(rs.getString("firearm_model"));
               competitionResultsRow.setDate(rs.getDate("date"));
               competitionResultsRow.setTotal_x(rs.getString("total_x"));
               competitionResultsRow.setTotal_ten(rs.getString("total_ten"));
               competitionResultsRow.setTotal_eight(rs.getString("total_eight"));
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
                "pocket_division, woman_division, senior_division, junior_division, limited_division, revolver_division, rimfire_division, " +
                "target_one_x, target_one_ten, target_one_eight, target_one_five, target_one_misses, target_two_x, " +
                "target_two_ten, target_two_eight, target_two_five, target_two_misses, penalty, final_score, total_x, " +
                "range_officer_initials, competitor_initials, additional_entry, last_modified) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now())";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(competitionResults.getCompetitionDetails().getCompetitionDetailsId()));
            ps.setInt(2, Integer.valueOf(competitionResults.getCompetitionDetails().getCompetitionCode().getCompetitionCodeId()));
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
            ps.setBoolean(12, competitionResults.getGssfIndoorScoreSheet().getDivsion().isLimited());
            ps.setBoolean(13, competitionResults.getGssfIndoorScoreSheet().getDivsion().isRevolver());
            ps.setBoolean(14, competitionResults.getGssfIndoorScoreSheet().getDivsion().isRimfire());
            ps.setInt(15, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getX());
            ps.setInt(16, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getTen());
            ps.setInt(17, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getEight());
            ps.setInt(18, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getFive());
            ps.setInt(19, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getMisses());
            ps.setInt(20, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getX());
            ps.setInt(21, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getTen());
            ps.setInt(22, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getEight());
            ps.setInt(23, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getFive());
            ps.setInt(24, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getMisses());
            ps.setInt(25, competitionResults.getGssfIndoorScoreSheet().getPenalty());
            ps.setInt(26, competitionResults.getGssfIndoorScoreSheet().getFinalScore());
            ps.setInt(27, competitionResults.getGssfIndoorScoreSheet().getTotalX());
            ps.setString(28, competitionResults.getGssfIndoorScoreSheet().getRangeOfficerInitials().toUpperCase());
            ps.setString(29, competitionResults.getGssfIndoorScoreSheet().getCompetitorInitials().toUpperCase());
            ps.setBoolean(30, competitionResults.getGssfIndoorScoreSheet().isAdditionalEntry());
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

    public void updateCompetitionResults(CompetitionResults competitionResults) throws Exception {

        String sql = "update competition_results set" +
                " target_one_x = ?" +
                " ,target_one_ten = ?" +
                " ,target_one_eight = ?" +
                " ,target_one_five = ?" +
                " ,target_one_misses = ?" +
                " ,target_two_x = ?" +
                " ,target_two_ten = ?" +
                " ,target_two_eight = ?" +
                " ,target_two_five = ?" +
                " ,target_two_misses = ?" +
                " ,penalty = ?" +
                " ,final_score = ?" +
                " ,total_x = ?" +
                " ,last_modified = now() where competition_results_id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getX());
            ps.setInt(2, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getTen());
            ps.setInt(3, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getEight());
            ps.setInt(4, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getFive());
            ps.setInt(5, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getMisses());
            ps.setInt(6, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getX());
            ps.setInt(7, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getTen());
            ps.setInt(8, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getEight());
            ps.setInt(9, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getFive());
            ps.setInt(10, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getMisses());
            ps.setInt(11, competitionResults.getGssfIndoorScoreSheet().getPenalty());
            ps.setInt(12, competitionResults.getGssfIndoorScoreSheet().getFinalScore());
            ps.setInt(13, competitionResults.getGssfIndoorScoreSheet().getTotalX());
            ps.setInt(14, competitionResults.getCompetitionResultsId());

            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to update Competition Results!" + ex.getMessage());
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

    public void addCompetitionResultsHistory(CompetitionResults competitionResults, String historyType) throws Exception {

        String sql = "insert into competition_results_history (id, code, competition_results_history.date, competitor_id, firearm_id, stock_division, unlimited_division, " +
                "pocket_division, woman_division, senior_division, junior_division, limited_division, revolver_division, rimfire_division, " +
                "target_one_x, target_one_ten, target_one_eight, target_one_five, target_one_misses, target_two_x, " +
                "target_two_ten, target_two_eight, target_two_five, target_two_misses, penalty, final_score, total_x, " +
                "range_officer_initials, competitor_initials, last_modified, competition_results_id, history_type, additional_entry) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, now(), ?, ?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(competitionResults.getCompetitionDetails().getCompetitionDetailsId()));
            ps.setInt(2, Integer.valueOf(competitionResults.getCompetitionDetails().getCompetitionCode().getCompetitionCodeId()));
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
            ps.setBoolean(12, competitionResults.getGssfIndoorScoreSheet().getDivsion().isLimited());
            ps.setBoolean(13, competitionResults.getGssfIndoorScoreSheet().getDivsion().isRevolver());
            ps.setBoolean(14, competitionResults.getGssfIndoorScoreSheet().getDivsion().isRimfire());
            ps.setInt(15, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getX());
            ps.setInt(16, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getTen());
            ps.setInt(17, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getEight());
            ps.setInt(18, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getFive());
            ps.setInt(19, competitionResults.getGssfIndoorScoreSheet().getTargetOne().getMisses());
            ps.setInt(20, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getX());
            ps.setInt(21, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getTen());
            ps.setInt(22, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getEight());
            ps.setInt(23, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getFive());
            ps.setInt(24, competitionResults.getGssfIndoorScoreSheet().getTargetTwo().getMisses());
            ps.setInt(25, competitionResults.getGssfIndoorScoreSheet().getPenalty());
            ps.setInt(26, competitionResults.getGssfIndoorScoreSheet().getFinalScore());
            ps.setInt(27, competitionResults.getGssfIndoorScoreSheet().getTotalX());
            ps.setString(28, competitionResults.getGssfIndoorScoreSheet().getRangeOfficerInitials().toUpperCase());
            ps.setString(29, competitionResults.getGssfIndoorScoreSheet().getCompetitorInitials().toUpperCase());
            ps.setInt(30, competitionResults.getCompetitionResultsId());
            ps.setString(31, historyType);
            ps.setBoolean(32, competitionResults.getGssfIndoorScoreSheet().isAdditionalEntry());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to add Competition Results History!" + ex.getMessage());
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

    public void deleteCompetitionResultByCompetitionResultsId(int key) throws Exception {

        String sql = "delete from competition_results where competition_results_id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, key);

            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to delete Competition Result by Competition Results Id!" + ex.getMessage());
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
