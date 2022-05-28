package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import java.sql.SQLException;
import Controller.database;
import Entity.Account;
import Secure.AES;
import javafx.collections.*;
public class AccountDAO  {
    public static ObservableList<Account> loadAccount(){
        ObservableList<Account> list_ac=FXCollections.observableArrayList();
        database db=new database();
        db.getConnect();
        ResultSet rs= db.execution("SELECT ac.userID,ac.userName,ac.userPassword,ac.startDay,ac.outofDay,ac.staffID,s.staffName FROM account ac join staff s on ac.staffid=s.staffID WHERE 1;");
        try {
            while(rs.next()){
                Account ac=new Account(rs.getInt(1));
                ac.setUserName(rs.getString(2));
                ac.setUserPassword(rs.getString(3));
                ac.setCreateday(rs.getString(4));
                ac.setOutofday(rs.getString(5));
                ac.setStaffID(rs.getInt(6));
                ac.setStaffName(AES.decrypt(rs.getString(7)));
                list_ac.add(ac);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error while loading account");
        }
        db.disconnect();
        return list_ac; 
    }
    public static Account getAccountByID(int id){
        database db=new database();
        db.getConnect();
        String sql="Select * FROM account WHERE userID='"+id+"'";
        ResultSet rs=db.execution(sql);
        try{
        while(rs.next()){
            Account ac=new Account(rs.getInt(1));
                ac.setUserPassword(rs.getString(2));
                ac.setStaffID(rs.getInt(3));
                ac.setCreateday(rs.getString(4));
                ac.setOutofday(rs.getString(5));
                db.disconnect();
                return ac;
                }
            }
            catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Can't get Account");
            }
            db.disconnect();
            return null;
    }
    public static void addAccount(Account ac){
        database db=new database();
        System.out.println("s:"+ac.getUserPassword()+ " idd:"+ac.getStaffID());
        try {
            db.getConnect();
            String sql= "INSERT INTO account(userName,userPassword,startDay,outofDay,staffID) VALUES (?,?,?,?,?)";
            PreparedStatement st= db.getCon().prepareStatement(sql);
            st.setString(1, ac.getUserName());
            st.setString(2, ac.getUserPassword());
            st.setString(3, ac.getCreateday());
            st.setString(4, ac.getOutofday());
            st.setInt(5, ac.getStaffID());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        db.disconnect();
    }
    public static void deleteAccount(int acID){
        database db=new database();
        try {
        db.getConnect();
        String sql = "DELETE FROM account WHERE account.userID= ?";
        PreparedStatement st= db.getCon().prepareStatement(sql);
        st.setInt(1, acID);
        st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        db.disconnect();
    }
    public static void editAccount(Account ac){
        database db=new database();
        try {
            db.getConnect();
            String sql="UPDATE account SET userName= ?,userPassword=?, staffID=?,startDay=?,outofDay=? WHERE account.userID=?";
            PreparedStatement st= db.getCon().prepareStatement(sql);
            st.setString(1, ac.getUserName());
            st.setString(2, ac.getUserPassword());
            st.setString(4, ac.getCreateday());
            st.setString(5, ac.getOutofday());
            st.setInt(3, ac.getStaffID());
            st.setInt(6,ac.getUserID());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
           // JOptionPane.showMessageDialog(null, "Error");
        }
        db.disconnect();
    }
    public List<Account> findAccount(String name,String addr,String phone,String mail) {
        List<Account> list_ac=new ArrayList();
        database db=new database();
        db.getConnect();
        String sql="SELECT ac.userID,s.staffName,r.roleName FROM account ac join Staff s on ac.staffID=s.staffID join Role r on s.staff_roleID=r.roleID WHERE ";
        if(!name.isEmpty()){
            sql +="s.staffName='"+name+"'";
        }
        if(!addr.isEmpty()){
            sql +="s.staffAddr='"+addr+"'";
        }
        if(!phone.isEmpty()){
            sql +="s.staffPhone='"+phone+"'";
        }
        if(!mail.isEmpty()){
            sql +="s.staffAddr='"+addr+"'";
    }
    sql=sql.substring(0, sql.length()-4);
    ResultSet rs=db.execution(sql);
    try {
        while(rs.next()){
            Account ac=new Account(rs.getInt(1));
            ac.setUserName(rs.getString(2));
            ac.setUserPassword(rs.getString(3));
            ac.setStaffID(rs.getInt(4));
            ac.setCreateday(rs.getString(5));
            ac.setOutofday(rs.getString(6));
            list_ac.add(ac);
        }
    } catch (Exception e) {        
        JOptionPane.showMessageDialog(null,"Can't find anything about this");
    }
    db.disconnect();
    return list_ac;
}
public Account findAccountByName(String userName) {
    database db=new database();
    db.getConnect();
    String sql="SELECT * FROM account WHERE account.username='%"+userName+"%'";
    ResultSet rs=db.execution(sql);
    try {
        while(rs.next()){
            Account ac=new Account(rs.getInt(1));
            ac.setUserName(rs.getString(2));
            ac.setUserPassword(rs.getString(3));
            ac.setStaffID(rs.getInt(4));
            ac.setCreateday(rs.getString(5));
            ac.setOutofday(rs.getString(6));
            return ac;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"Can't find anything about this");
    }
    db.disconnect();
    return null;
    }

    public static int findIDByName(String staffName){
    	staffName = AES.encrypt(staffName);
        int ID=0;
        database db = new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT staffID From staff WHERE staffName='"+staffName+"'");
        try {
            while(rs.next()){
                ID=rs.getInt(1);
            }
        } catch (Exception e) {
            
        }
        db.disconnect();
        return ID;
    }
}