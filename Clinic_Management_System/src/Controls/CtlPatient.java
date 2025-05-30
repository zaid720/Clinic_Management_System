/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import Connect.ClsConnect;
import Interface.IPatient;
import Models.MPatient;
import Models.MUpcomingAppointment;
import java.io.IOException;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author ITS
 */
public class CtlPatient implements IPatient {

    private ArrayList<MPatient> array = new ArrayList<MPatient>();
    private ArrayList<MUpcomingAppointment> list = new ArrayList<MUpcomingAppointment>();
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public void insert(MPatient obj) {
        try {
            String sql = "insert into tbl_patients (Full_name, Age, Gender, Phone, Address, Medication_notes, Created_at) values (?, ?, ?, ?, ?, ?, ?)";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setString(1, obj.getFull_name());
            stmt.setInt(2, obj.getAge());
            stmt.setString(3, obj.getGender());
            stmt.setString(4, String.valueOf(obj.getPhone()));
            stmt.setString(5, obj.getAddress());
            stmt.setString(6, obj.getMedicat());
            stmt.setTimestamp(7, Timestamp.valueOf(obj.getCreated_at()));
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
            // التحقق مما إذا كان المريض لديه مواعيد
            String checkSql = "SELECT COUNT(*) FROM tbl_Appointments WHERE Patient_id = ?";
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
                    String sql = "update tbl_patients Inner join tbl_Appointments as app On tbl_Patients.ID = app.Patient_id  set tbl_Patients.Active = ?, app.Active = ?  where tbl_Patients.ID = ? and app.Patient_id = ?";
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
                // التحديث فقط في جدول المرضى
                String updatePatient = "UPDATE tbl_Patients SET Active = false WHERE ID = ?";
                PreparedStatement updatePatientStmt = ClsConnect.getConnection().prepareStatement(updatePatient);
                updatePatientStmt.setInt(1, id);
                updatePatientStmt.executeUpdate();
                ClsConnect.getConnection().close();
                updatePatientStmt.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void update(MPatient obj) {
        try {
            String sql = "update tbl_patients set Full_name = ?, Age = ?, Gender = ?, Phone = ?, Address = ?, Medication_notes = ? where ID = ?";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setString(1, obj.getFull_name());
            stmt.setInt(2, obj.getAge());
            stmt.setString(3, obj.getGender());
            stmt.setString(4, obj.getPhone());
            stmt.setString(5, obj.getAddress());
            stmt.setString(6, obj.getMedicat());
            stmt.setInt(7, obj.getId());
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
    public MPatient getById(Integer id) {
        return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    @Override
    public ArrayList<MUpcomingAppointment> upcomingAppointments() {
        list.clear();
        try {
            String sql = "select tbl_patients.Full_name, tbl_appointments.Appointment_date, tbl_appointments.Status from tbl_patients Inner Join tbl_appointments on tbl_patients.ID = tbl_appointments.Patient_id where tbl_patients.Active = 1";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                if (rs.getString("Status").equalsIgnoreCase("قادم")) {
                    list.add(new MUpcomingAppointment(rs.getString("Full_name"), rs.getDate("Appointment_date").toLocalDate()));
                }
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
        return list;
    }

    @Override
    public ArrayList<MPatient> getAll() /*throws DataNotFoundException*/ {
        array.clear();
        String sql = "select ID, Full_name, Age, Gender, Phone, Address, Medication_notes, Created_at from tbl_patients  where Active = 1";
        try {
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            /* if(rs.next() ==false) 
                throw new  DataNotFoundException("not found");*/
            while (rs.next()) {
                array.add(new MPatient(rs.getInt("ID"), rs.getString("Full_name"), rs.getInt("Age"), rs.getString("Gender"), rs.getString("Phone"), rs.getString("Address"), rs.getString("Medication_notes"), rs.getTimestamp("Created_at").toLocalDateTime()));
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
        return array;
    }

    @Override
    public Integer count() {
        return getAll().size();
    }

}
