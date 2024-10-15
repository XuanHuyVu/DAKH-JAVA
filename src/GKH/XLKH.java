package GKH;

import java.sql.Connection; // Correct import for SQL Connection
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class XLKH implements IKhachhang {
    private static Connection cn;

    public Connection getCon() {
        if (cn == null) {
            try {
                cn = DriverManager.getConnection("jdbc:sqlserver://XUY\\SQLEXPRESS;database=DLKH;user=sa;password=12345678;trustServerCertificate=true");
                System.out.println("Connected.");
            } catch (SQLException e) {
                System.out.println("Not Connected! " + e.getMessage());
            }
        }
        return cn;
    }

    public ResultSet getbySTK(String SoTK) {
        getCon();
        try {
            Statement st = cn.createStatement();
            return st.executeQuery("SELECT * FROM tbKH WHERE SoTK = '" + SoTK + "'");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public boolean updateKH(String SoTK, Khachhang kh) {
        try {
            PreparedStatement pst = cn.prepareStatement("UPDATE tbKH SET Hoten = ?, GT = ?, Diachi = ?, Sodu = ? WHERE SoTK = ?");
            pst.setString(1, kh.getHoten());
            pst.setString(2, kh.getGT());
            pst.setString(3, kh.getDiachi());
            pst.setString(4, kh.getSodu());
            pst.setString(5, SoTK);
            int res = pst.executeUpdate();
            return res > 0;
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
