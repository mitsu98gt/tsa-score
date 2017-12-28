package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResults;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class CompetitionResultsDAOImpl implements CompetitionResultsDAO {

    private DataSource dataSource;

    public void addCompetitionResults(CompetitionResults competitionResults) throws Exception {

        String sql = "insert into competition_results (id, code, competition_results.date, competitor_id, firearm_id, stock_division, unlimited_division, " +
                "pocket_division, woman_division, senior_division, junior_division, " +
                "target_one_x, target_one_ten, target_one_eight, target_one_five, target_one_misses, target_two_x, " +
                "target_two_ten, target_two_eight, target_two_five, target_two_misses, penalty, " +
                "range_officer_initials, competitor_initials) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
            ps.setString(23, competitionResults.getGssfIndoorScoreSheet().getRangeOfficerInitials());
            ps.setString(24, competitionResults.getGssfIndoorScoreSheet().getCompetitorInitials());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException ex) {
            throw new Exception("Failed to add Competition Results!" + ex.getMessage());
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
