/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GKH;
import java.sql.*;
/**
 *
 * @author Admin
 */
public interface IKhachhang {
    public Connection getCon();
    public ResultSet getbySTK(String SoTK);
    public boolean updateKH(String SoTK, Khachhang kh);
}
