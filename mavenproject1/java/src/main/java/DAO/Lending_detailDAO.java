package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import Controller.database;
import Entity.Book;
import Entity.BookLending;
import Entity.Lending_Detail;
import Secure.AES;
import javafx.collections.*;
public class Lending_detailDAO {
    public static ObservableList<Lending_Detail> load(){
        ObservableList<Lending_Detail> l_lenddetail = FXCollections.observableArrayList();
        database db = new database();
        db.getConnect();
        try {
        	String sql = "SELECT ld.lendID,b.bookName,ld.dueDay,ld.lendStatus,ld.bookID FROM lending_detail ld join booklending bl on ld.lendID=bl.lendID join book b on ld.bookID=b.bookID WHERE 1 ORDER BY lendID ASC;";
        	PreparedStatement st = db.getCon().prepareStatement(sql);
        	ResultSet rs = st.executeQuery();
            while(rs.next()){
                Lending_Detail ld=new Lending_Detail();
                ld.setLendID(rs.getInt(1));
                ld.setBookName(AES.decrypt(rs.getString(2)));
                ld.setDueDay(rs.getString(3));
                ld.setLendStatus(rs.getString(4));
                ld.setBookID(rs.getInt(5));
                l_lenddetail.add(ld);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Lending book detail error");
        }
        db.disconnect();
        return l_lenddetail;
    }
    public static void addLend(Lending_Detail ld){
        try {
            database db = new database();
            db.getConnect();
            String sql = "INSERT INTO lending_detail (lendID,bookID,dueDay,lendStatus) VALUES (?,?,?,?)";
            PreparedStatement st = db.getCon().prepareStatement(sql);
            st.setInt(1,ld.getLendID());
            st.setInt(2, ld.getBookID());
            st.setString(3, ld.getDueDay());
            st.setString(4, ld.getLendStatus());
            st.executeUpdate();
            db.disconnect();     
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error to add borrow");
            e.printStackTrace();
        }
       
    }
    public static void editLend(Lending_Detail ld){
        database db=new database();
        db.getConnect();
        try {
            String sql="UPDATE lending_detail SET dueDay=?,lendStatus=? WHERE lendID=? AND bookID=?";
            PreparedStatement st = db.getCon().prepareStatement(sql);
            st.setString(1,ld.getDueDay());
            st.setString(2, ld.getLendStatus());
            st.setInt(3,ld.getLendID());
            st.setInt(4, ld.getBookID());
            st.executeUpdate();
        db.update(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }        
        db.disconnect();
    }
    public static void deleteLend(int lendID){
        try {
            database db = new database();
        db.getConnect();
        db.update("SET FOREIGN_KEY_CHECKS=0;");
        db.update("DELETE FROM lending_detail WHERE lendID='"+lendID+"';");
        db.update("SET FOREIGN_KEY_CHECKS=1;");
        db.disconnect();    
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static Lending_Detail findLendByID(int lendID,int bookID){
        database db = new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT * From lending_detail WHERE lendID='"+ lendID+"' and bookID='"+bookID+"';");
        try {
            while(rs.next()){
                Lending_Detail ld = new Lending_Detail();
                ld.setLendID(rs.getInt(1));
                ld.setBookID(rs.getInt(2));
                ld.setDueDay(rs.getString(3));
                ld.setLendStatus(rs.getString(4));
                return ld;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Not founnd detail");
        }
        db.disconnect();
        return null;
    }
    public static int newLendID(){
        database db=new database();
        db.getConnect();
        ResultSet rs=db.execution("SELECT Max(lendID) FROM booklending;");
        try{
            while(rs.next()){
                int id=rs.getInt(1);
                db.disconnect();
                return id;
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"No  detail found");
        }
        db.disconnect();
        return -1;
    }
        
}