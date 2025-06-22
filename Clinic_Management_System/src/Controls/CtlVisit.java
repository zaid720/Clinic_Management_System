package Controls;

import Connect.ClsConnect;
import Interface.IVisit;
import Models.MVisit;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CtlVisit implements IVisit {

    private ArrayList<MVisit> array = new ArrayList<MVisit>();
    private ArrayList<Integer> numAppointment = new ArrayList<Integer>();
    private PreparedStatement stmt;
    private ResultSet rs;

    @Override
    public Integer count() {
        try {
            return getAll().size();
        } catch (DataNotFoundException ex) {
            return 0;
        }
    }

    @Override
    public ArrayList<MVisit> getAll() throws DataNotFoundException {
        array.clear();
        try {
            String sql = "select v.ID, a.ID, a.Appointment_date, a.Status, v.Visit_date, v.Diagnosis, v.Notes, v.Created_at"
                    + " from tbl_visits AS v "
                    + "INNER JOIN tbl_appointments AS a ON v.Appointment_id = a.ID "
                    + "where v.Active = 1";

            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                array.add(new MVisit(rs.getInt("v.ID"), rs.getInt("a.ID"), rs.getDate("a.Appointment_date").toLocalDate(), rs.getString("a.Status"), rs.getDate("v.Visit_date").toLocalDate(), rs.getString("v.Diagnosis"), rs.getString("v.Notes"), rs.getTimestamp("v.Created_at").toLocalDateTime()));
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
    public void insert(MVisit obj) {
        try {
            String sql = "insert into tbl_visits (Appointment_id, Visit_date, Diagnosis, Notes, Created_at) values (?, ?, ?, ?, ?)";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setInt(1, obj.getNumAppointment());
            stmt.setDate(2, Date.valueOf(obj.getVisit_date()));
            stmt.setString(3, obj.getDiagnosis());
            stmt.setString(4, obj.getNotes());
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
            String sql = "update tbl_visits set Active = ? where ID = ?";
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
    public void update(MVisit obj) {
        try {
            String sql = "update tbl_visits set Appointment_id = ?, Visit_date = ?, Diagnosis = ?, Notes = ? where ID = ?";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            stmt.setInt(1, obj.getNumAppointment());
            stmt.setDate(2, Date.valueOf(obj.getVisit_date()));
            stmt.setString(3, obj.getDiagnosis());
            stmt.setString(4, obj.getNotes());
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
    public MVisit getById(Integer id) {
        try {
            return getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
        } catch (DataNotFoundException ex) {
            return null;
        }
    }

    @Override
    public ArrayList<Integer> qeuryNumAppointment() {
        numAppointment.clear();
        try {
            String sql = "select ID from tbl_appointments where Active = 1";
            stmt = ClsConnect.getConnection().prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                numAppointment.add(rs.getInt("ID"));
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
        return numAppointment;
    }

}
