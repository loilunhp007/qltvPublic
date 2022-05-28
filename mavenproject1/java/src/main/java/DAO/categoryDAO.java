package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.JOptionPane;

import Controller.database;
import Entity.Categories;
import javafx.collections.*;
public class categoryDAO {
    public static ObservableList<Categories> load() {
        ObservableList<Categories> l_cate = FXCollections.observableArrayList();
        database db=new database();
        db.getConnect();
        try {
        	String sql="SELECT * FROM categories";
        	PreparedStatement st = db.getCon().prepareStatement(sql);
        	ResultSet rs = st.executeQuery();
            while(rs.next()){
                Categories cate = new Categories(rs.getInt(1));
                cate.setCategoryName(rs.getString(2));
                l_cate.add(cate);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error while loading Categories");
        }
        db.disconnect();
        return l_cate;
    }
    public static void addcategories(Categories cate){
        database db = new database();
        db.getConnect();
       try{
        String sql = "INSERT INTO `categories` (categoryName) VALUES (?)";
    	PreparedStatement st = db.getCon().prepareStatement(sql);
        st.setString(1, cate.getCategoryName());
        st.executeUpdate();
       }catch(Exception e){
        e.printStackTrace();
        e.getMessage();        
    }
        db.disconnect();
    }
    public static void deletecate(int categoryID){
        database db = new database();
        db.getConnect();
        try{db.update("DELETE FROM categories WHERE categoryID="+categoryID);}
        catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        db.disconnect();
    }
    public static void editcategories(Categories cate){
        database db=new database();
        db.getConnect();
        try {
            String sql="UPDATE categories SET categoryName=? WHERE categoryID=?";
        PreparedStatement st = db.getCon().prepareStatement(sql);
        st.setString(1, cate.getCategoryName());
        st.setInt(2, cate.getCategoryID());
        st.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error");
        }        
        db.disconnect();
    }
    public static Categories findcategoriesByID(int categoryID){
        database db = new database();
        db.getConnect();
        
        try {
        	String sql="SELECT * From categories WHERE categoryID=?";
            PreparedStatement st = db.getCon().prepareStatement(sql);
            st.setInt(1, categoryID);
            ResultSet rs =st.executeQuery();
            while(rs.next()){
                Categories cate = new Categories(rs.getInt(1));
                cate.setCategoryName(rs.getString(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Can't find anything about this");
        }
        db.disconnect();
        return null;
    }

    public static int findRoleByName(String categoryName){
        int categoryid=0;
        database db = new database();
        db.getConnect();
        try {
        	String sql="SELECT categoryID From categories WHERE categoryName=? LIMIT 1";
            PreparedStatement st = db.getCon().prepareStatement(sql);
            st.setString(1, categoryName);
            ResultSet rs =st.executeQuery();
            while(rs.next()){
                categoryid=rs.getInt(1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Can't find anything about this");
        }
        db.disconnect();
        return categoryid;
    }
}