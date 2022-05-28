package Controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.AlreadyConnectedException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.swing.JOptionPane;
import javafx.scene.control.Alert.*;
import Controller.database;
import DAO.bookDAO;
import DAO.staffDAO;
import Entity.*;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.event.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.stage.Stage;
public class exportController implements Initializable {
    @FXML 
    private FileInputStream fis;
    @FXML 
    private Button export;
    @FXML
    private JFXButton menuBtn;
    @FXML 
    private ComboBox<String> cbox;
    
    ObservableList Option =FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCombobox();
    }
    public void exportBtn(ActionEvent event) throws SQLException{
        String choice=cbox.getSelectionModel().getSelectedItem();
        database db=new database();
        db.getConnect();
        try {
            
        } catch (NullPointerException e) {
            Alert a=new Alert(AlertType.ERROR, "Empty Table");
            a.show();
        }
        if(choice.equals("staff") ){            
            try {
            XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("Staff Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("staffID");
            header.createCell(1).setCellValue("staffName");
            header.createCell(2).setCellValue("staffdob");
            header.createCell(3).setCellValue("staffAddr");
            header.createCell(4).setCellValue("staffGender");
            header.createCell(5).setCellValue("staffPhone");
            header.createCell(6).setCellValue("role_name");
            int index=1;
            ResultSet rs = db.execution("SELECT s.staffID,s.staffName,s.staffdob,s.staffAddr,s.staffGender,s.staffPhone,r.roleName From staff s join role r on s.staff_roleID = r.roleID;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(3));
                row.createCell(3).setCellValue(rs.getString(4));
                row.createCell(4).setCellValue(rs.getString(5));
                row.createCell(5).setCellValue(rs.getString(6));
                row.createCell(6).setCellValue(rs.getString(7));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("staff.xlsx");
            wb.write(fo);
            fo.close();
            JOptionPane.showMessageDialog(null,"Staff has been export");
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null,"Table not found");
            }
            catch(IOException ex){
                JOptionPane.showMessageDialog(null,"ERROR WHILE EXPORT");
            }
        }
        if(choice.equals("account") ){
            try {
                XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("Account Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("userID");
            header.createCell(1).setCellValue("userName");
            header.createCell(2).setCellValue("userPassword");
            header.createCell(3).setCellValue("startDay");
            header.createCell(4).setCellValue("outofDay");
            header.createCell(5).setCellValue("staffName");
            int index=1;
            ResultSet rs= db.execution("SELECT ac.userID,ac.userName,ac.userPassword,ac.startDay,ac.outofDay,s.staffName FROM account ac join staff s on ac.staffid=s.staffID WHERE 1;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(3));
                row.createCell(3).setCellValue(rs.getString(4));
                row.createCell(4).setCellValue(rs.getString(5));
                row.createCell(5).setCellValue(rs.getString(6));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("account.xlsx");
            wb.write(fo);
            fo.close();
            db.disconnect();
            JOptionPane.showMessageDialog(null,"Account has been export");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"error");
            }
        }
        if(choice.equals("author") ){
            try {
            XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("Author Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("authorID");
            header.createCell(1).setCellValue("authorName");
            header.createCell(2).setCellValue("authorGender");
            header.createCell(3).setCellValue("authorDOB");
            header.createCell(4).setCellValue("authorEmail");
            int index=1;
            ResultSet rs = db.execution("SELECT * From author;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(3));
                row.createCell(3).setCellValue(rs.getString(4));
                row.createCell(4).setCellValue(rs.getString(5));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("author.xlsx");
            wb.write(fo);
            fo.close();
            db.disconnect();
            JOptionPane.showMessageDialog(null,"Author has been export");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"error");
            }
        }
        if(choice.equals("book") ){
            try {
            XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("Book Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("BookID");
            header.createCell(1).setCellValue("BookName");
            header.createCell(2).setCellValue("BookAuthor");
            header.createCell(3).setCellValue("BookCategory");
            header.createCell(4).setCellValue("BookPublisher");
            header.createCell(5).setCellValue("BookPrice");
            header.createCell(6).setCellValue("BookPages");
            int index=1;
            ResultSet rs = db.execution("SELECT b.bookID,b.bookName,b.bookAuthorID,b.bookCategoryID,b.bookPublisher,b.bookPrice,b.bookPages,a.authorName,cate.categoryName From book b left join author a on b.bookAuthorID=a.authorID left join categories cate on b.bookCategoryID=cate.categoryID ;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(8));
                row.createCell(3).setCellValue(rs.getString(9));
                row.createCell(4).setCellValue(rs.getString(5));
                row.createCell(5).setCellValue(rs.getString(6));
                row.createCell(6).setCellValue(rs.getString(7));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("Book.xlsx");
            wb.write(fo);
            fo.close();
            db.disconnect();
            JOptionPane.showMessageDialog(null,"Book has been export");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"error");
            }
        }
        if(choice.equals("booklending") ){
            try {
                XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("BookLending Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("lendID");
            header.createCell(1).setCellValue("StudentName");
            header.createCell(2).setCellValue("StartDate");
            header.createCell(3).setCellValue("Returndate");
            header.createCell(4).setCellValue("StaffName");
            header.createCell(5).setCellValue("Total");
            int index=1;
            ResultSet rs= db.execution("SELECT bl.lendID,st.studentName,bl.createDay,bl.returndate,s.staffName,count(*) FROM booklending bl join student st on bl.lendStudentID = st.studentID join staff s on s.staffID=bl.issued_by join lending_detail ld on bl.lendID=ld.lendID WHERE ld.lendStatus='Lending' GROUP BY ld.lendID;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(3));
                row.createCell(3).setCellValue(rs.getString(4));
                row.createCell(4).setCellValue(rs.getString(5));
                row.createCell(5).setCellValue(rs.getInt(6));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("BookLending.xlsx");
            wb.write(fo);
            fo.close();
            db.disconnect();
            JOptionPane.showMessageDialog(null,"BookLending has been export");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"error");
            }
        }
        if(choice.equals("lending_detail") ){
            try {
                XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("Lending Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("lendID");
            header.createCell(1).setCellValue("BookName");
            header.createCell(2).setCellValue("DueDay");
            header.createCell(3).setCellValue("LendStatus");
            header.createCell(4).setCellValue("LendStudentID");
            int index=1;
            ResultSet rs= db.execution("SELECT ld.lendID,b.bookName,ld.dueDay,ld.lendStatus,bl.lendStudentID FROM lending_detail ld join booklending bl on ld.lendID=bl.lendID join book b on ld.bookID=b.bookID WHERE 1 ORDER BY lendID ASC;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(3));
                row.createCell(3).setCellValue(rs.getString(4));
                row.createCell(4).setCellValue(rs.getInt(5));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("lending_detail.xlsx");
            wb.write(fo);
            fo.close();
            db.disconnect();
            JOptionPane.showMessageDialog(null,"Lending_detail has been export");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"error");
            }
        }
        if(choice.equals("student") ){
            try {
            XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("Student Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("StudentID");
            header.createCell(1).setCellValue("StudentName");
            header.createCell(2).setCellValue("StudentDOB");
            header.createCell(3).setCellValue("StudentEmail");
            header.createCell(4).setCellValue("StudentClass");
            int index=1;
            ResultSet rs = db.execution("SELECT * FROM student;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                row.createCell(2).setCellValue(rs.getString(3));
                row.createCell(3).setCellValue(rs.getString(4));
                row.createCell(4).setCellValue(rs.getString(5));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("student.xlsx");
            wb.write(fo);
            fo.close();
            db.disconnect();
            JOptionPane.showMessageDialog(null,"Student has been export");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"error");
            }
        }
        if(choice.equals("categories") ){
            try {
            XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("Category Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("CategoryID");
            header.createCell(1).setCellValue("Category Name");
            int index=1;
            ResultSet rs = db.execution("SELECT * FROM categories;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("categories.xlsx");
            wb.write(fo);
            fo.close();
            db.disconnect();
            JOptionPane.showMessageDialog(null,"Category has been export");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"error");
            }
        }
        if(choice.equals("role") ){
            try {
            XSSFWorkbook wb= new XSSFWorkbook();
            XSSFSheet sheet= wb.createSheet("ROLE Details");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("ROLE ID");
            header.createCell(1).setCellValue("ROLE Name");
            int index=1;
            ResultSet rs = db.execution("SELECT * FROM role;");
            while(rs.next()){
                XSSFRow  row= sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt(1));
                row.createCell(1).setCellValue(rs.getString(2));
                index++;
            }
            FileOutputStream fo=new FileOutputStream("role.xlsx");
            wb.write(fo);
            fo.close();
            db.disconnect();
            JOptionPane.showMessageDialog(null,"ROLE has been export");
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,"error");
            }
        }
        db.disconnect();

    }
    public void fillCombobox(){
        database db=new database();
        try {
            db.getConnect();
              String sql="SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_SCHEMA='qltv1' ";
              ResultSet rs=db.execution(sql);
              while(rs.next()){
                  Option.add(rs.getString(1));
              }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error while loading combobox");
        }
        db.disconnect();
        cbox.setItems(Option);

    }
    @FXML
    public void menuOpen() throws Exception{
        menuBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../View/Function.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
}