package DAO;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.sound.sampled.SourceDataLine;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JOptionPane;
import javax.xml.transform.SourceLocator;

import Controller.database;
import Entity.Staff;
import Secure.AES;
import javafx.scene.control.*;
import javafx.collections.*;
public class staffDAO {
    public static ObservableList<Staff> load(){
        ObservableList<Staff> l_staff=FXCollections.observableArrayList();
        database db=new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT s.staffID,s.staffName,s.staffdob,s.staffAddr,s.staffGender,s.staffPhone,r.roleName,s.staffSalary,s.staffImg From staff s join role r on s.staff_roleID = r.roleID;");
        try {
            while(rs.next()){
                Staff staff = new Staff(rs.getInt(1));
                staff.setStaffName(AES.decrypt(rs.getString(2)));
                staff.setStaffDOB(rs.getString(3));
                staff.setStaffAddr(AES.decrypt(rs.getString(4)));
                staff.setStaffGender(rs.getString(5));
                staff.setStaffPhone(AES.decrypt(rs.getString(6)));
                staff.setRole_name(rs.getString(7));
                staff.setStaffSalary(rs.getInt(8));
                staff.setBlob(rs.getBlob(9));
                l_staff.add(staff);
            }
        } catch (Exception e) {
        }
        db.disconnect();
        return l_staff;
    }
    public static void addStaff(Staff staff){
        database db = new database();
        db.getConnect();
        try {
            String sql = "INSERT INTO staff (staffName,staffdob,staffAddr,staffGender,staffPhone,staff_roleID,staffSalary,staffImg) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement st= db.getCon().prepareStatement(sql);
            st.setString(1, staff.getStaffName());
            st.setString(2, staff.getStaffDOB());
            st.setString(3, staff.getStaffAddr());
            st.setString(4, staff.getStaffGender());
            st.setString(5, staff.getStaffPhone());
            st.setInt(6, staff.getStaff_role());
            st.setInt(7, staff.getStaffSalary());
            byte[] buff = staff.getStaffImg().getBytes();
            Blob blob = new SerialBlob(buff);
            st.setBlob(8, blob);
            db.updateStaff(st);
        } catch (Exception e) {
            return;
        }
        db.disconnect();
    }
    public static void deleteStaff(int staffID){
        database db = new database();
        db.getConnect();
        db.update("DELETE FROM staff WHERE staffID="+staffID);
        db.disconnect();
    }
    public static void editStaff(Staff staff){
        database db=new database();
        db.getConnect();
        try {
            String sql="UPDATE staff SET staffName=?, staffdob=?, staffAddr=?, staffGender=?, staffPhone=?, staff_roleID=?, staffSalary=?, staffImg=? WHERE staffID="+staff.getStaffID()+";";
            PreparedStatement st = db.getCon().prepareStatement(sql);
            st.setString(1, staff.getStaffName());
            st.setString(2, staff.getStaffDOB());
            st.setString(3, staff.getStaffAddr());
            st.setString(4, staff.getStaffGender());
            st.setString(5, staff.getStaffPhone());
            st.setInt(6, staff.getStaff_role());
            st.setInt(7, staff.getStaffSalary());
            byte[] buff = staff.getStaffImg().getBytes();
            Blob blob = new SerialBlob(buff);
            st.setBlob(8, blob);
            db.updateStaff(st);
        } catch (Exception e) {
        	e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }        
        db.disconnect();
    }

    /*    public static void editStaff(Staff staff){
        database db=new database();
        db.getConnect();
        try {
            String sql="UPDATE staff SET ";
            sql+="staffName='"+ staff.getStaffName()+"',";
            sql+="staffdob='"+ staff.getStaffDOB()+"',";
            sql+="staffAddr='"+ staff.getStaffAddr()+"',";
            sql+="staffGender='"+ staff.getStaffGender()+"',";
            sql+="staffPhone='"+staff.getStaffPhone()+"',";
            sql+="staff_roleID='"+staff.getStaff_role()+"',";
            sql+="staffSalary='"+staff.getStaffSalary()+"',";
            sql+="staffImg=LOAD_FILE('"+staff.getStaffImg()+"'),";
            sql+="staffPhone='"+staff.getStaffPhone()+"' WHERE staffID="+staff.getStaffID()+";";
            db.update(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }        
        db.disconnect();
    }*/
    public static Staff findstaffByID(int staffID){
        database db = new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT * From staff WHERE staffID="+staffID);
        try {
            while(rs.next()){
                Staff staff = new Staff(rs.getInt(1));
                staff.setStaffName(AES.decrypt(rs.getString(2)));
                staff.setStaffDOB(rs.getString(3));
                staff.setStaffAddr(AES.decrypt(rs.getString(4)));
                staff.setStaffGender(rs.getString(5));
                staff.setStaffPhone(AES.decrypt(rs.getString(6)));
                staff.setStaff_role(rs.getInt(7));
                staff.setStaffSalary(rs.getInt(8));
                staff.setBlob(rs.getBlob(9));
                return staff;
            }
        } catch (Exception e) {
            
        }
        db.disconnect();
        return null;
    }
    public static int findRoleByName(String roleName){
        int role=0;
        database db = new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT roleID From role WHERE roleName='"+roleName+"'");
        try {
            while(rs.next()){
                role=rs.getInt(1);
            }
        } catch (Exception e) {
            
        }
        db.disconnect();
        return role;
    }
}