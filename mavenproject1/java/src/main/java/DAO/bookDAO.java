package DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.io.File;
import java.io.FileInputStream;
import javafx.scene.control.Alert.*;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JOptionPane;
import javafx.scene.control.*;
import Controller.database;
import Entity.Book;
import Secure.AES;
import javafx.collections.*;
public class bookDAO{

    public static ObservableList<Book> load() {
        ObservableList<Book> l_book =FXCollections.observableArrayList();
        database db = new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT b.bookID,b.bookName,b.bookAuthorID,b.bookCategoryID,b.bookPublisher,b.bookPrice,b.available,a.authorName,cate.categoryName,b.bookImg From book b left join author a on b.bookAuthorID=a.authorID left join categories cate on b.bookCategoryID=cate.categoryID ;");
        try {
            while(rs.next()){
                Book book = new Book(rs.getInt(1));
                book.setBookName(AES.decryptFromAES(rs.getString(2)));
                book.setBookAuthorID(rs.getInt(3));
                book.setBookCategoryID(rs.getInt(4));
                book.setBookPublisher(rs.getInt(5));
                book.setBookPrice(rs.getInt(6));
                book.setAvailable(rs.getInt(7));
                book.setBookAuthor(AES.decrypt(rs.getString(8)));
                book.setBookCategory(rs.getString(9));
                book.setBlob(rs.getBlob(10));
                l_book.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error while loading book");
        }
        db.disconnect();
        return l_book;
        

    }
    public static  void addBook(Book book) throws Exception{
        database db = new database();
        db.getConnect();
        try {
                      
            String sql = "INSERT INTO book (bookName,bookAuthorID,bookCategoryID,bookPublisher,bookPrice,available,bookImg) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement st= db.getCon().prepareStatement(sql);
            st.setString(1, book.getBookName());
            //sql +=book.getBookName()+"','";
            st.setInt(2, book.getBookAuthorID());
            //sql += book.getBookAuthorID() +"','";
            st.setInt(3, book.getBookCategoryID());
            //sql += book.getBookCategoryID() +"','";
            st.setInt(4, book.getBookPublisher());
            //sql +=book.getBookPublisher()+"','";
            st.setInt(5, book.getBookPrice());
            //sql +=book.getBookPrice()+"','";
            st.setInt(6, book.getAvailable());
            byte[] buff = book.getBookImg().getBytes();
            Blob blob = new SerialBlob(buff);
            //sql +=book.getBookPages()+"');";
            st.setBlob(7, blob);
            db.updateStaff(st);
        } catch (NullPointerException e) {
            Alert a=new Alert(AlertType.ERROR,"Please fill all field before Add book");
            a.show();
        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        db.disconnect();
    }
    public static void deleteBook(int bookID){
        database db = new database();
        db.getConnect();
        try{        
        db.update("DELETE FROM book WHERE bookID="+bookID);
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        db.disconnect();
    }
    public static void editBook(Book book){
        database db=new database();
        db.getConnect();
        try {
            String sql="UPDATE book SET bookName=?, bookAuthorID=?, bookCategoryID=?, bookPublisher=?, bookPrice=?, available=?, bookImg=? WHERE bookID=?";
            PreparedStatement st = db.getCon().prepareStatement(sql);
            st.setString(1, book.getBookName());
            //sql +=book.getBookName()+"','";
            st.setInt(2, book.getBookAuthorID());
            //sql += book.getBookAuthorID() +"','";
            st.setInt(3, book.getBookCategoryID());
            //sql += book.getBookCategoryID() +"','";
            st.setInt(4, book.getBookPublisher());
            //sql +=book.getBookPublisher()+"','";
            st.setInt(5, book.getBookPrice());
            //sql +=book.getBookPrice()+"','";
            st.setInt(6, book.getAvailable());
            //sql +=book.getBookPages()+"');";
            byte[] buff = book.getBookImg().getBytes();
            Blob blob = new SerialBlob(buff);
            st.setBlob(7, blob);
            st.setInt(8, book.getBookID());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }        
        db.disconnect();
    }
    public static Book findBookByID(int bookID){
        database db = new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT * From book WHERE bookID="+bookID);
        try {
            while(rs.next()){
                Book book = new Book(rs.getInt(1));
                book.setBookName(AES.decrypt(rs.getString(2)));
                book.setBookAuthorID(rs.getInt(3));
                book.setBookCategoryID(rs.getInt(4));
                book.setBookPublisher(rs.getInt(5));
                book.setBookPrice(rs.getInt(6));
                book.setAvailable(rs.getInt(7));
                book.setBlob(rs.getBlob(8));
                return book;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Can't find anything about this");
        }
        db.disconnect();
        return null;
    }
    public static void forlend(int bookID){
        database db=new database();
        db.getConnect();
        try {
            Book book=new Book();
            book=findBookByID(bookID);
            int available=(book.getAvailable()-1);
            if(available > 0){
                String sql="UPDATE book SET ";
                sql+="available='"+available+"' WHERE bookID="+book.getBookID()+";";
                db.update(sql);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error While Lending book");
        }        
        db.disconnect();
    }
    public static void returnlend(int bookID){
        database db=new database();
        db.getConnect();
        try {
            Book book=new Book();
            book=findBookByID(bookID);
            int available=book.getAvailable()+1;
                String sql="UPDATE book SET ";
                sql+="available='"+available+"' WHERE bookID="+book.getBookID()+";";
                db.update(sql);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error While Returning book");
        }        
        db.disconnect();
    }
}