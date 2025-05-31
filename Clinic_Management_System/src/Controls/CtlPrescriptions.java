/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import Connect.ClsConnect;
import Interface.IPrescriptions;
import Models.MPrescriptions;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ITS
 */
public class CtlPrescriptions implements IPrescriptions {

    private PreparedStatement stmt;
    private ResultSet rs;
    private ArrayList<MPrescriptions> array = new ArrayList<MPrescriptions>();
    private ArrayList<Integer> numVisit = new ArrayList<Integer>();

    @Override
    public ArrayList<MPrescriptions> getAll() throws DataNotFoundException {
        array.clear();
        try {
            String sql = "select pr.ID, v.ID, pr.Medicatoin, pr.Dosage, pr.Duration, pr.Created_at "
                    + "from tbl_prescriptions as pr "
                    + "Inner join tbl_visits as v on pr.Visit_id = v.ID "
                    + "where pr.Active = 1";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                array.add(new MPrescriptions(rs.getInt("pr.ID"), rs.getInt("v.ID"), rs.getString("pr.Medicatoin"), rs.getString("pr.Dosage"), rs.getString("pr.Duration"), rs.getTimestamp("pr.Created_at").toLocalDateTime()));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ClsConnect.getConnection().close();
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        if (array.size() <= 0) {
            throw new DataNotFoundException("Not found data");
        }

        return array;
    }

    @Override
    public void insert(MPrescriptions obj) {
        try {
            String sql = "insert into tbl_prescriptions (Visit_id, Medicatoin, Dosage, Duration, Created_at) values (?, ?, ?, ?, ?)";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setInt(1, obj.getNumVisit());
            stmt.setString(2, obj.getMedication());
            stmt.setString(3, obj.getDosage());
            stmt.setString(4, obj.getDuration());
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ClsConnect.getConnection().close();
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void remove(Integer id) {
        try {
            String sql = "update tbl_prescriptions set Active = ? where ID = ?";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setBoolean(1, false);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ClsConnect.getConnection().close();
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void update(MPrescriptions obj) {
        try {
            String sql = "update tbl_prescriptions set Visit_id = ?, Medicatoin = ?, Dosage = ?, Duration = ? where ID = ?";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setInt(1, obj.getNumVisit());
            stmt.setString(2, obj.getMedication());
            stmt.setString(3, obj.getDosage());
            stmt.setString(4, obj.getDuration());
            stmt.setInt(5, obj.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ClsConnect.getConnection().close();
                stmt.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public MPrescriptions getById(Integer id) {
        try {
            return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        } catch (DataNotFoundException ex) {
            return null;
        }
    }

    @Override
    public Integer count() {
        try {
            return getAll().size();
        } catch (DataNotFoundException ex) {
            return 0;
        }
    }

    @Override
    public ArrayList<Integer> qeuryNumVisit() {
        numVisit.clear();
        try {
            String sql = "select ID from tbl_visits where Active = 1";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                numVisit.add(rs.getInt("ID"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                ClsConnect.getConnection().close();
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return numVisit;
    }
}
