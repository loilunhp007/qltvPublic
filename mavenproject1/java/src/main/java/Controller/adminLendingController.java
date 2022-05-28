package Controller;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import Controller.database;
import DAO.Lending_detailDAO;
import DAO.authorDAO;
import DAO.bookDAO;
import DAO.bookLendingDAO;
import DAO.staffDAO;
import DAO.studentDAO;
import Entity.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.util.StringConverter;
import javafx.event.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert.*;
public class adminLendingController implements Initializable {
    
    @FXML
    private TableView<BookLending> tableLend;
    @FXML
    private TableColumn<BookLending, Integer> t_ID;
    @FXML
    private TableColumn<BookLending, String> t_name;
    @FXML
    private TableColumn<BookLending, String> t_create;
    @FXML
    private TableColumn<BookLending, String> t_return;
    @FXML
    private TableColumn<BookLending, String> t_staff;
    @FXML
    private TableColumn<BookLending, Integer > t_total;
    @FXML
    private Button addBtn;
    @FXML
    private Button checkbookBtn;
    @FXML
    private Button checkbookBtn1;
    @FXML
    private Button checkbookBtn2;
    @FXML
    private Button checkcardBtn;
    @FXML
    private Button checkstaffBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private TextField lendID;
    @FXML
    private TextField cardID;
    @FXML
    private TextField bookID;
    @FXML
    private TextField book2ID;
    @FXML
    private TextField book3ID;
    @FXML
    private TextField bookName1;
    @FXML
    private TextField bookName2;
    @FXML
    private DatePicker setReturn;
    @FXML
    private TextField book;
    @FXML
    private TextField staffID;
    @FXML
    private TextField studentName;
    @FXML
    private TextField studentClass;
    @FXML
    private TextField staffName;
    //Book Lending end
    @FXML
    private TableView<Lending_Detail> tableReturn;
    @FXML
    private TableColumn<Student, Integer> r_ID;
    @FXML
    private TableColumn<Student, String> r_name;
    @FXML
    private TableColumn<Student, String> r_returndate;
    @FXML
    private TableColumn<Student, String> r_status;
    @FXML
    private Button returnBook;
    @FXML
    private Button clear1;
    @FXML
    private Button checkreturnID;
    @FXML

    private TextField RlendID;
    @FXML
    private TextField RcardID;
    @FXML
    private TextField RbookID;
    @FXML
    private TextField RbookAuthor;
    @FXML
    private TextField Rbook;
    @FXML
    private TextField RstudentName;
    @FXML
    private TextField RstudentClass;
    @FXML
    private TextField Rprice;
    //Book return END
    ObservableList<BookLending> lendList = FXCollections.observableArrayList();
    ObservableList<Lending_Detail> returnList = FXCollections.observableArrayList();
    @FXML
    private Button logoutBtn;
    @FXML
    private Button checkRbookID;

    @Override
    public void initialize( URL url, ResourceBundle rb){
        convertDate();
        loadLend();
        loadDetail();
        onSelect();
        onSelectlending();
    }
    public void convertDate(){
       String pattern="yyyy-MM-dd";
       setReturn.setPromptText(pattern.toLowerCase());
       setReturn.setConverter(new StringConverter<LocalDate>(){
        DateTimeFormatter DTF=DateTimeFormatter.ofPattern(pattern);
           @Override
           public String toString(LocalDate t) {
               if(t !=null){
                   return DTF.format(t);
               }
               return null;
           }
    
           @Override
           public LocalDate fromString(String string) {
               if(string !=null && !string.isEmpty()){
                   return LocalDate.parse(string,DTF);
               }
               return null;
           }
       }); 
    }
    public void loadLend() {
        database db=new database();
        try {
            
            db.getConnect();
            //ResultSet rs=db.execution("SELECT * FROM student");
            //while(rs.next()){
                //studentList.add(new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getInt(7)));
                lendList=bookLendingDAO.load();
            //}
        } catch (Exception e) {
            Logger.getLogger(adminLendingController.class.getName());
        }
        db.disconnect();
        t_ID.setCellValueFactory(new PropertyValueFactory<>("lendID"));
        t_name.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        t_create.setCellValueFactory(new PropertyValueFactory<>("createDay"));
        t_return.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        t_staff.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        t_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        tableLend.setItems(lendList);     
    }
    public void loadDetail(){
        database db=new database();
        try {
            db.getConnect();
            returnList=Lending_detailDAO.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.disconnect();
        r_ID.setCellValueFactory(new PropertyValueFactory<>("lendID"));
        r_name.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        r_returndate.setCellValueFactory(new PropertyValueFactory<>("dueDay"));
        r_status.setCellValueFactory(new PropertyValueFactory<>("lendStatus"));
        tableReturn.setItems(returnList);
    }
    //Check book
    @FXML
    public void checkBook(ActionEvent event) throws Exception{
        try {
            int bookid1=Integer.parseInt(bookID.getText());
        Book b1=new Book();
        b1=bookDAO.findBookByID(bookid1);
        if(b1.getAvailable() == 0){
            bookID.setText(null);
            JOptionPane.showMessageDialog(null,"Books are not Available");
        }
        else{
            book.setText(b1.getBookName());
           
        }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Empty ID");
        }

    }
    @FXML
    public void checkBook1(ActionEvent event) throws Exception{
        try {
                int bookid=Integer.parseInt(book2ID.getText());
                Book b=new Book();
                b=bookDAO.findBookByID(bookid);
                if(b.getAvailable()>0){
                    bookName1.setText(b.getBookName());
                }
                else{
                    book2ID.setText(null);
                    JOptionPane.showMessageDialog(null,"Books are not Available");
                }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Empty ID");
        }
    }
    @FXML
    public void checkBook2(ActionEvent event) throws Exception{
        try {
                int bookid=Integer.parseInt(book3ID.getText());
                Book b=new Book();
                b=bookDAO.findBookByID(bookid);
                if(b.getAvailable()>0){
                    bookName2.setText(b.getBookName());
                }
                else{
                    book3ID.setText(null);
                    JOptionPane.showMessageDialog(null,"Books are not Available");
                }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"Empty ID");
        }
    }
//Check book end

//Check card student

    @FXML
    public void checkCard(ActionEvent event) throws Exception{
    try {
        int cardid1=Integer.parseInt(cardID.getText());
    Student st1=new Student();
    st1=studentDAO.findStudentByID(cardid1);
    studentName.setText(st1.getStudentName());
    studentClass.setText(st1.getStudentClass());    
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"No Student Found");
    }
    
}
    @FXML
    public void checkReturnStudent(ActionEvent event) throws Exception{
    try {
        int returnid=Integer.parseInt(RlendID.getText());
        BookLending bl=new BookLending();
        bl=bookLendingDAO.findLendByID(returnid);
        Student st=new Student();
        st=studentDAO.findStudentByID(bl.getLendStudentID());
        RcardID.setText(Integer.toString(st.getStudentID()));
        String stname=st.getStudentName();
        String stclass=st.getStudentClass();
        lendID.setText(Integer.toString(bl.getLendStudentID()));
        RstudentName.setText(stname);
        RstudentClass.setText(stclass);

    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "No student or lendID Found");
    }
}
    @FXML
    public void checkReturnBook(ActionEvent event) throws Exception{
    try {
        int returnbook=Integer.parseInt(RbookID.getText());
        Book b=new Book();
        b=bookDAO.findBookByID(returnbook);
        String bname=b.getBookName();
        int bauthorID=b.getBookAuthorID();
        Author aut =new Author();
        aut=authorDAO.findAuthorByID(bauthorID);
        String bauthor=aut.getAuthorName();
        String price1=Integer.toString(b.getBookPrice());
        Rbook.setText(bname);
        RbookAuthor.setText(bauthor);
        Rprice.setText(price1);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "No book Found");
    }
}
//Check card student end

//staff check
    @FXML
    public void checkStaff(ActionEvent event) throws Exception{
    try {
        int staffid1=Integer.parseInt(staffID.getText());
        Staff staff1=new Staff();
        staff1=staffDAO.findstaffByID(staffid1);
        staffName.setText(staff1.getStaffName());    
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null,"No Staff Found");
    }
    
}

//staff check end
    //        ADD borrow Start
    
    @FXML
    public void addLendBtn(ActionEvent event) throws Exception{
        try {
        BookLending bl = new BookLending();
        Lending_Detail ld= new Lending_Detail();
        int cardid1=Integer.parseInt(cardID.getText());
        LocalDate date1= LocalDate.now();
        String create1=date1.toString();
        String returndate=setReturn.getValue().toString();
        int staff1=Integer.parseInt(staffID.getText());
        bl.setLendStudentID(cardid1);
        bl.setCreateDay(create1);
        bl.setReturnDate(returndate);
        bl.setIssued_by(staff1);
        bookLendingDAO.addLend(bl);
        //add to booklending
        int lendid1=Lending_detailDAO.newLendID();
        String book1=book.getText();   
        String out= null;
        String txtID=bookID.getText();
        int bookID=Integer.parseInt(txtID);
        ld.setLendID(lendid1);
        ld.setBookID(bookID);
        ld.setDueDay(out);
        ld.setLendStatus("Lending");//add to detail
        Lending_detailDAO.addLend(ld);
        bookDAO.forlend(ld.getBookID());
        if(book2ID.getText()!=null){
        Lending_Detail ld2= new Lending_Detail();
        String txtID1=book2ID.getText();
        int bookID1=Integer.parseInt(txtID1);
        ld2.setLendID(lendid1);
        ld2.setBookID(bookID1);
        ld2.setDueDay(out);
        ld2.setLendStatus("Lending");
        Lending_detailDAO.addLend(ld2);
        bookDAO.forlend(ld2.getBookID());
        //add to detail
        }
        if(book3ID.getText()!=null){
        Lending_Detail ld3= new Lending_Detail();
        String txtID2=book3ID.getText();
        int bookID2=Integer.parseInt(txtID2);
        ld3.setLendID(lendid1);
        ld3.setBookID(bookID2);
        ld3.setDueDay(out);
        ld3.setLendStatus("Lending");//add to detail
        Lending_detailDAO.addLend(ld3);
        bookDAO.forlend(ld3.getBookID());
        clearALL();
        loadLend();
        loadDetail();
        }
    } 
        catch (NumberFormatException e) {
            loadLend();
            loadDetail();
            if(bookID==null&&book2ID==null&&book3ID==null){
                JOptionPane.showMessageDialog(null,"TextField can not null");
            }
        }
    }


    //Borrowed book End
    public void updateLendBtn(ActionEvent event) throws Exception{
        try {
            BookLending bl = new BookLending();
        int idd=Integer.parseInt(lendID.getText());
        String cardid1=cardID.getText();
        int cardid=Integer.parseInt(cardid1);
        String  staff1=staffID.getText();
        int issued_by=Integer.parseInt(staff1);
        LocalDate date1=setReturn.getValue();
        String date2=date1.toString();
        bl.setLendID(idd);
        bl.setLendStudentID(cardid);
        bl.setIssued_by(issued_by);
        bl.setReturnDate(date2);
        bookLendingDAO.editLend(bl);
        loadLend();
        clearALL();
        } catch (NumberFormatException e) {
            Alert a = new Alert(AlertType.ERROR, "Please Fill all the field\nUpdateLending Fail");
            a.show();
        }
    }

//delete start
    public void removeLendBtn(ActionEvent event){
        try {
            Alert.AlertType type=Alert.AlertType.CONFIRMATION;
        Alert al=new Alert(type,"");
        al.setTitle("Confirm");
        al.setContentText("Are you sure you want to delete this?");
        Optional<ButtonType> res= al.showAndWait();
        if(res.get() == ButtonType.OK){
            int idd=Integer.parseInt(lendID.getText());
            Lending_detailDAO.deleteLend(idd);
            bookLendingDAO.deleteLend(idd);
            loadLend();
            loadDetail();
            clearALL();
        }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,"No lendID found");
        }
      
    }
//delete end   
    
    //          Add student end
    public void onSelect() {
        this.tableReturn.setRowFactory(param -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                Lending_Detail ld = this.tableReturn.getSelectionModel().getSelectedItem();
                BookLending bl2=new BookLending();
                this.RlendID.setText(Integer.toString(ld.getLendID()));
                int id=ld.getLendID();
                bl2=bookLendingDAO.findLendByID(id);
                this.RcardID.setText(Integer.toString(bl2.getLendStudentID()));
                Student st=new Student();
                st=studentDAO.findStudentByID(bl2.getLendStudentID());
                this.RstudentName.setText(st.getStudentName());
                this.RstudentClass.setText(st.getStudentClass());
                Book b=new Book();
                b=bookDAO.findBookByID(ld.getBookID());
                this.RbookID.setText(Integer.toString(ld.getBookID()));
                this.Rbook.setText(b.getBookName());
                Author aut=new Author();
                aut=authorDAO.findAuthorByID(b.getBookAuthorID());
                this.RbookAuthor.setText(aut.getAuthorName());
                this.Rprice.setText(Integer.toString(b.getBookPrice()));
            });
            return row;
        });
    }
    public void onSelectlending() {
        this.tableLend.setRowFactory(param -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                BookLending bl = this.tableLend.getSelectionModel().getSelectedItem();
                this.lendID.setText(Integer.toString(bl.getLendID()));
                this.cardID.setText(Integer.toString(bl.getLendStudentID()));
                Student st=new Student();
                st=studentDAO.findStudentByID(bl.getLendStudentID());
                this.studentName.setText(st.getStudentName());
                this.studentClass.setText(st.getStudentClass());
                String sdate=bl.getReturnDate();
                LocalDate sDate2=LocalDate.parse(sdate);
                this.setReturn.setValue(sDate2);
            });
            return row;
        });
    }
    @FXML
    public void clearALL(){
        lendID.clear();
        bookID.clear();
        book2ID.clear();
        book3ID.clear();
        book.clear();
        bookName1.clear();
        bookName2.clear();
        cardID.clear();
        studentName.clear();
        studentClass.clear();
        setReturn.setValue(null);
        staffID.clear();
        staffName.clear();
    }
    // return book
    @FXML
    public void ReturnBtn(ActionEvent event){
        try {
            Lending_Detail ld1=new Lending_Detail();
        ld1=Lending_detailDAO.findLendByID(Integer.parseInt(RlendID.getText()),Integer.parseInt(RbookID.getText()));
        if(ld1.getLendStatus().equals("Lending")){
            Lending_Detail ld=new Lending_Detail();
        ld.setLendID(Integer.parseInt(RlendID.getText()));
        ld.setBookID(Integer.parseInt(RbookID.getText()));
        LocalDate date1=LocalDate.now();
        String returndate=date1.toString();
        ld.setDueDay(returndate);
        ld.setLendStatus("Returned");
        Lending_detailDAO.editLend(ld);
        bookDAO.returnlend(ld.getBookID());
        loadDetail();
        loadLend();       
        }
        else{
            JOptionPane.showMessageDialog(null,"This ID  already Returned");
        }    
        } catch (NumberFormatException e) {
            Alert al=new Alert(AlertType.ERROR,"Please choose a detail\nReturn Fail");
            al.show();
        }       
    }
    public void get_accountID(int ID){
        staffID.setText(Integer.toString(ID));
    }
    public void logoutOpen() throws Exception{
    logoutBtn.getScene().getWindow().hide();
    Parent root = FXMLLoader.load(getClass().getResource("../View/Function.fxml"));
            Stage mainStage=new Stage();
            Scene scene=new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
    }
    public void RclearALL(){
        RlendID.clear();
        RcardID.clear();
        RbookID.clear();
        Rbook.clear();
        RbookAuthor.clear();
        Rprice.clear();
        RstudentName.clear();
        RstudentClass.clear();
    }
}