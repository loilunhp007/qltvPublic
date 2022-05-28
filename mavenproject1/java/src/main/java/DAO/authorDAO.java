package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import Controller.database;
import Entity.Author;
import Secure.AES;
import javafx.collections.*;
public class authorDAO {
    public static ObservableList<Author> load(){
        ObservableList<Author> l_Author=  FXCollections.observableArrayList();
        database db=new database();
        db.getConnect();
        String sql = "SELECT * From author";
       
        try {
        	 PreparedStatement st= db.getCon().prepareStatement(sql);
             ResultSet rs = st.executeQuery();
            while(rs.next()){
                Author author = new Author(rs.getInt(1));
                author.setAuthorName(rs.getString(2));
                author.setAuthorGender(rs.getString(3));
                author.setAuthorDOB(rs.getString(4));
                author.setAuthorEmail(rs.getString(5));
                l_Author.add(author);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error while loading author");
        }
        db.disconnect();
        return l_Author;
    }
    public static void addAuthor(Author Author){
        database db = new database();
        db.getConnect();
        try{
            String sql = "INSERT INTO author (authorName,authorGender,authorDOB,authorEmail ) VALUES (?,?,?,?)";
            PreparedStatement st= db.getCon().prepareStatement(sql);
            st.setString(1,Author.getAuthorName());
            st.setString(2,Author.getAuthorGender());
            st.setString(3,Author.getAuthorDOB());
            st.setString(4,Author.getAuthorEmail());
            st.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }
        db.disconnect();
    }
    public static void deleteAuthor(int authorID){
        database db = new database();
        db.getConnect();
        try{
        db.update("DELETE FROM Author WHERE authorID="+authorID);
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        db.disconnect();
    }
    public static void editAuthor(Author author){
        database db=new database();
        db.getConnect();
        try {
        	 String sql="UPDATE Author SET authorName= ?,authorGender=?,authorDOB=?,authorEmail=? WHERE authorID=? ";
        	PreparedStatement st = db.getCon().prepareStatement(sql);
        	st.setString(1,author.getAuthorName());
            st.setString(2,author.getAuthorGender());
            st.setString(3,author.getAuthorDOB());
            st.setString(4,author.getAuthorEmail());
            st.setInt(5, author.getAuthorID());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }        
        db.disconnect();
    }
    public static Author findAuthorByID(int authorID){
        database db = new database();
        db.getConnect();
        try {
        	PreparedStatement st  = db.getCon().prepareStatement("SELECT * From author WHERE authorID= ?");
        	st.setInt(1, authorID);
        	ResultSet rs = st.executeQuery();
            while(rs.next()){
                Author author = new Author(rs.getInt(1));
                author.setAuthorName(AES.decrypt(rs.getString(2)));
                author.setAuthorGender(rs.getString(3));
                author.setAuthorDOB(rs.getString(4));
                author.setAuthorEmail(rs.getString(5));
                return author;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Can't find anything about this");
        }
        db.disconnect();
        return null;
    }

    public static int findRoleByName(String authorName){
        int authorid=0;
        database db = new database();
        db.getConnect();
        try {
        	PreparedStatement st  = db.getCon().prepareStatement("SELECT authorID From author WHERE authorName=?");
        	st.setString(1, authorName);
        	ResultSet rs = st.executeQuery();
            while(rs.next()){
                authorid=rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Can't find anything about this");
            
        }
        db.disconnect();
        return authorid;
    }
}