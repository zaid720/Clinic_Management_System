package Controls;

import Connect.ClsConnect;
import Interface.IAppointment;
import Models.MAppointment;
import Models.MQeuryName;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class CtlAppointment implements IAppointment {

    private ArrayList<MAppointment> array = new ArrayList<MAppointment>();
    private ArrayList<MQeuryName> items = new ArrayList<MQeuryName>();
    PreparedStatement stmt;
    ResultSet rs;

    @Override
    public Integer count() {
        try {
            return getAll().size();
        } catch (DataNotFoundException ex) {
            return 0;
        }
    }

    @Override
    public ArrayList<MAppointment> getAll() throws DataNotFoundException {
        array.clear();
        try {
            String sql = "SELECT a.ID, p.Full_name, u.Username, a.Appointment_date, a.Appointment_time, a.Status, a.Created_at "
                    + "FROM tbl_appointments AS a "
                    + "INNER JOIN tbl_patients AS p ON a.Patient_id = p.ID "
                    + "INNER JOIN tbl_users AS u ON a.Doctor_id = u.ID "
                    + "where a.Active = 1";

            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                array.add(new MAppointment(rs.getInt("a.ID"), rs.getString("p.Full_name"), rs.getString("u.Username"), rs.getDate("Appointment_date").toLocalDate(), rs.getTime("Appointment_time").toLocalTime(), rs.getString("Status"), rs.getTimestamp("Created_at").toLocalDateTime()));
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
    public void insert(MAppointment obj) {
        try {
            String sql = "insert into tbl_appointments (Patient_id, Doctor_id, Appointment_date, Appointment_time, Status, Created_at) values (?, ?, ?, ?, ?, ?)";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setInt(1, obj.getIdPatitent());
            stmt.setInt(2, obj.getIdDoctor());
            stmt.setDate(3, Date.valueOf(obj.getAppointment_date()));
            stmt.setTime(4, Time.valueOf(obj.getAppointment_time()));
            stmt.setString(5, obj.getStatus());
            stmt.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
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
            String sql = "update tbl_appointments set Active = ? where ID = ?";
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
    public void update(MAppointment obj) {
        try {
            String sql = "update tbl_appointments set Patient_id = ?, Doctor_id = ?, Appointment_date = ?, Appointment_time = ?, Status = ? where ID = ?";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setInt(1, obj.getIdPatitent());
            stmt.setInt(2, obj.getIdDoctor());
            stmt.setDate(3, Date.valueOf(obj.getAppointment_date()));
            stmt.setTime(4, Time.valueOf(obj.getAppointment_time()));
            stmt.setString(5, obj.getStatus());
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
    public MAppointment getById(Integer id) {
        try {
            return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        } catch (DataNotFoundException ex) {
            return null;
        }
    }

    @Override
    public ArrayList<MQeuryName> namePatientQeury() {
        items.clear();
        try {
            String sql = "select ID, Full_name from tbl_patients where Active = 1";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new MQeuryName(rs.getInt("ID"), rs.getString("Full_name")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return items;
    }

    @Override
    public ArrayList<MQeuryName> nameDoctorQeury() {
        items.clear();
        try {
            String sql = "select ID, Username from tbl_users where Active = 1 and Role = 'Doctor'";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new MQeuryName(rs.getInt("ID"), rs.getString("Username")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return items;
    }

}
