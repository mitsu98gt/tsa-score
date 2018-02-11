package com.envisageconsulting.primefaces.scoredaddy.dao.impl;

import com.envisageconsulting.primefaces.scoredaddy.dao.FirearmDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;
import com.envisageconsulting.primefaces.scoredaddy.domain.FirearmBrand;
import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FirearmDAOImpl implements FirearmDAO {

    private DataSource dataSource;

    public Firearm getFirearmById(String id) throws Exception {

        String sql = "select id, model from firearm_models where id = ?";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(id));
            ResultSet rs = ps.executeQuery();

            Firearm firearm = new Firearm();
            while (rs.next()) {
                firearm.setId(Integer.toString(rs.getInt("id")));
                firearm.setModel(rs.getString("model"));
            }
            rs.close();
            ps.close();
            return firearm;
        } catch (SQLException ex) {
            throw new Exception("Failed to get Firearm by Id!" + ex);
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

    public List<Firearm> getGlockFirearmForScoreSheet() throws Exception {

        String sql = "select id, model from firearm_models where brand_id = 1 order by model";

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
            throw new Exception("Failed to get all Glock Models!" + ex);
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

    public List<Firearm> getAllFirearmsForScoreSheet() throws Exception {

        //String sql = "select id, model from firearm_models order by model";
        String sql = "select fm.id, fb.brand, fm.model from firearm_models fm, firearm_brands fb where fm.brand_id = fb.id order by brand, model";
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
                firearm.setBrand(rs.getString("brand"));
                firearmList.add(firearm);
            }
            rs.close();
            ps.close();
            return firearmList;
        } catch (SQLException ex) {
            throw new Exception("Failed to get all firearms Models!" + ex);
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

    public List<FirearmBrand> getAllFirearmBrands() throws Exception {

        String sql = "select id, brand from firearm_brands order by brand";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            List<FirearmBrand> firearmBrandList = new ArrayList<FirearmBrand>();

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FirearmBrand firearmBrand =  new FirearmBrand();
                firearmBrand.setId(rs.getString("id"));
                firearmBrand.setBrand(rs.getString("brand"));
                firearmBrandList.add(firearmBrand);
            }
            rs.close();
            ps.close();
            return firearmBrandList;
        } catch (SQLException ex) {
            throw new Exception("Failed to get all Firearm Brands!" + ex);
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

    public void addFirearm(Firearm firearm) throws  Exception {

        String sql = "insert into firearm_models (brand_id, model) values (?, ?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.valueOf(firearm.getBrand()));
            ps.setString(2, firearm.getModel());
            ps.executeUpdate();

            ps.close();

        } catch (SQLException ex) {
            throw new Exception("Failed to add Firearm!" + ex.getMessage());
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

    public void addFirearmBrand(String brand) throws  Exception {

        String sql = "insert into firearm_brands (brand) values (?)";

        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, brand);
            ps.executeUpdate();

            ps.close();

        } catch (SQLException ex) {
            throw new Exception("Failed to add Firearm Brand!" + ex.getMessage());
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
