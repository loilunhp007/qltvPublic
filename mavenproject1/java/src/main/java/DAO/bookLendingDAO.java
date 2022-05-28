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
import Secure.AES;
import javafx.collections.*;

public class bookLendingDAO {
    public static ObservableList<BookLending> load(){
        ObservableList<BookLending> l_lending = FXCollections.observableArrayList();
        database db = new database();
       
        try {
        	String sql = "SELECT bl.lendID,st.studentName,bl.createDay,bl.returndate,s.staffName,count(*),bl.lendStudentID,bl.issued_by FROM booklending bl join student st on bl.lendStudentID = st.studentID join staff s on s.staffID=bl.issued_by join lending_detail ld on bl.lendID=ld.lendID WHERE ld.lendStatus='Lending' GROUP BY ld.lendID;";         			
        	PreparedStatement st = db.getCon().prepareStatement(sql);
        	 ResultSet rs= st.executeQuery();
            while(rs.next()){
                BookLending bl=new BookLending(rs.getInt(1));
                bl.setStudentName(AES.decrypt(rs.getString(2)));
                bl.setCreateDay(rs.getString(3));
                bl.setReturnDate(rs.getString(4));
                bl.setStaffName(AES.decrypt(rs.getString(5)));
                bl.setTotal(rs.getInt(6));
                bl.setLendStudentID(rs.getInt(7));
                bl.setIssued_by(rs.getInt(8));
                l_lending.add(bl);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Lending book error");
        }
        db.disconnect();
        return l_lending;
    }
    public static void addLend(BookLending bl){
        try {
            database db = new database();
            db.getConnect();
            String sql = "INSERT INTO booklending (lendStudentID,createDay,returndate,issued_by) VALUES ('";
            PreparedStatement st = db.getCon().prepareStatement(sql);
            st.setInt(1, bl.getLendStudentID());
            st.setString(2, bl.getCreateDay());
            st.setString(3, bl.getReturnDate());
            st.setInt(4, bl.getIssued_by());
            st.executeUpdate();
            db.disconnect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Student just can lend 3 books");
        }
    }
    public static void editLend(BookLending bl){
        database db=new database();
        db.getConnect();
        try {
            String sql="UPDATE booklending SET lendStudentID= ? ,returndate=?,issued_by=? WHERE lendID=?";
        PreparedStatement st = db.getCon().prepareStatement(sql);
        st.setInt(1, bl.getLendStudentID());
        st.setString(2, bl.getCreateDay());
        st.setString(3, bl.getReturnDate());
        st.setInt(4, bl.getIssued_by());
        st.setInt(5, bl.getLendID());
        st.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }        
        db.disconnect();
    }
    public static void deleteLend(int lendID){
        database db = new database();
        db.getConnect();
        try{
        db.update("SET FOREIGN_KEY_CHECKS=0;");
        db.update("DELETE FROM booklending WHERE lendID='"+lendID+"';");
        db.update("SET FOREIGN_KEY_CHECKS=1;");
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        db.disconnect();
    }
    public static BookLending findLendByID(int lendID){
        database db = new database();
        db.getConnect();
        
        try {
        	String sql = "SELECT * From booklending WHERE lendID= ?";
        	PreparedStatement st = db.getCon().prepareStatement(sql);
        	st.setInt(1,lendID);
        	ResultSet rs = st.executeQuery();
            while(rs.next()){
                BookLending bl = new BookLending(rs.getInt(1));
                bl.setLendStudentID(rs.getInt(2));
                bl.setCreateDay(rs.getString(3));
                bl.setReturnDate(rs.getString(4));
                bl.setIssued_by(rs.getInt(5));
                return bl;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Can't find anything about this");
        }
        db.disconnect();
        return null;
    }
    public static BookLending findLendByStudentID(int studentID){
        database db = new database();
        db.getConnect();
        try {
        	String sql = "SELECT * From booklending WHERE lendStudentID= ?";
        	PreparedStatement st = db.getCon().prepareStatement(sql);
        	st.setInt(1,studentID);
        	ResultSet rs = st.executeQuery();
            while(rs.next()){
                BookLending bl = new BookLending(rs.getInt(1));
                bl.setLendStudentID(rs.getInt(2));
                bl.setCreateDay(rs.getString(3));
                bl.setIssued_by(rs.getInt(4));
                return bl;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Can't find anything about this");
        }
        db.disconnect();
        return null;
    }
}