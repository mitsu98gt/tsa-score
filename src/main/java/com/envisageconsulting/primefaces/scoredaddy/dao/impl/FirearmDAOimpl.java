package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.FirearmDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FirearmDAOimpl implements FirearmDAO {

    private DataSource dataSource;

    public List<Firearm> getFirearmForScoreSheet() throws Exception {

        String sql = "select id, model from firearm_models order by model";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<Firearm> firearmList = new ArrayList<Firearm>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Firearm firearm =  new Firearm();
                firearm.setId(Integer.toString(rs.getInt("id")));
                firearm.setModel(rs.getString("model"));
                firearmList.add(firearm);
            }
            rs.close();
            ps.close();
            return firearmList;
        } catch (SQLException ex) {
            throw new Exception(ex);
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
