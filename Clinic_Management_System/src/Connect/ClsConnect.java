package Connect;

import constance.Constances;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClsConnect {
    
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(Constances.url, Constances.user, Constances.password);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex);
        }
        return null;
    }
    
}
