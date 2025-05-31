/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import Connect.ClsConnect;
import Interface.IUser;
import Models.MUser;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author ITS
 */
public class CtlUser implements IUser {

    private ArrayList<MUser> array = new ArrayList<MUser>();
    private PreparedStatement stmt;
    ResultSet rs;

    @Override
    public ArrayList<MUser> qeuryLogin(String name) {
        array.clear();
        String sqlLogin = "Select * from tbl_users where Username = ? And Active = 1";
        try {
            stmt = ClsConnect.getConnection().prepareStatement(sqlLogin);
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            while (rs.next()) {
                array.add(new MUser(rs.getString("Username"), rs.getString("Password"), rs.getString("Role"), rs.getString("Full_name"), rs.getString("Phone")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        } finally {
            try {
                ClsConnect.getConnection().close();
                stmt.close();
                rs.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return array;
    }

    @Override
    public ArrayList<MUser> getAll() throws DataNotFoundException {
        array.clear();
        String sqlQeury = "Select * from tbl_users where Active = 1";
        try {
            stmt = ClsConnect.getConnection().prepareStatement(sqlQeury);
            rs = stmt.executeQuery();
            while (rs.next()) {
                array.add(new MUser(rs.getInt("ID"), rs.getString("Username"), rs.getString("Password"), rs.getString("Role"), rs.getString("Full_name"), rs.getString("Phone"), rs.getTimestamp("Created_at").toLocalDateTime()));
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
            throw new DataNotFoundException("not Found Data");
        }
        
        return array;
    }

    @Override
    public void insert(MUser obj) {
        try {
            String sql = "Insert into tbl_users (Username, Password, Role, Full_name, Phone, Created_at) values (?, ?, ?, ?, ?, ?)";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setString(1, obj.getUsername());
            stmt.setString(2, obj.getPassword());
            stmt.setString(3, obj.getRole());
            stmt.setString(4, obj.getFull_name());
            stmt.setString(5, obj.getPhone());
            stmt.setTimestamp(6, Timestamp.valueOf(obj.getCreated_at()));
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
    public void update(MUser obj) {
        try {
            String sql = "update tbl_users set Username = ?, Password = ?, Role = ?, Full_name = ?, Phone = ? where ID = ?";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setString(1, obj.getUsername());
            stmt.setString(2, obj.getPassword());
            stmt.setString(3, obj.getRole());
            stmt.setString(4, obj.getFull_name());
            stmt.setString(5, obj.getPhone());
            stmt.setInt(6, obj.getId());
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
            // التحقق مما إذا كان الطبيب لديه مواعيد
            String checkSql = "SELECT COUNT(*) FROM tbl_Appointments WHERE Doctor_id = ?";
            PreparedStatement checkStmt = ClsConnect.getConnection().prepareStatement(checkSql);
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();

            boolean hasAppointments = false;
            if (rs.next()) {
                hasAppointments = rs.getInt(1) > 0;
            }
            rs.close();
            checkStmt.close();

            if (hasAppointments) {
                // التحديث في كلا الجدولين
                try {
                    String sql = "UPDATE tbl_users "
                            + "JOIN tbl_Appointments AS app ON tbl_users.ID = app.Doctor_id "
                            + "SET tbl_users.Active = ?, app.Active = ? "
                            + "WHERE tbl_users.ID = ? AND app.Doctor_id = ?";
                    stmt = ClsConnect.getConnection().prepareStatement(sql);
                    stmt.setBoolean(1, false);
                    stmt.setBoolean(2, false);
                    stmt.setInt(3, id);
                    stmt.setInt(4, id);
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
            } else {
                // التحديث فقط في جدول المستخدمين
                String updateDoctor = "UPDATE tbl_users SET Active = false WHERE ID = ?";
                PreparedStatement updateDoctorStmt = ClsConnect.getConnection().prepareStatement(updateDoctor);
                updateDoctorStmt.setInt(1, id);
                updateDoctorStmt.executeUpdate();
                ClsConnect.getConnection().close();
                updateDoctorStmt.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public MUser getById(Integer id) {
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

}
