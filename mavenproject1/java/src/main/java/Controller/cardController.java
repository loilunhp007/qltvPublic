package Controller;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.Date;
import java.util.logging.Logger;
import Controller.database;
import DAO.studentDAO;
import Entity.*;
import Secure.AES;

import com.jfoenix.controls.JFXButton;
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
public class cardController implements Initializable {
    
    @FXML
    private TableView<Student> tableCard;
    @FXML
    private TableColumn<Student, Integer> t_ID;
    @FXML
    private TableColumn<Student, String> t_name;
    @FXML
    private TableColumn<Student, String> t_dob;
    @FXML
    private TableColumn<Student, String> t_email;
    @FXML
    private TableColumn<Student, String> t_class;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button clear;
    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField email;
    @FXML
    private TextField studentclass;
    @FXML
    private TextField search;
   
    ObservableList<Student> cardList= FXCollections.observableArrayList();
    @Override
    public void initialize( URL url, ResourceBundle rb){
        convertDate();
        loadCard();
        onSelect();
    }
    public void convertDate(){
       String pattern="yyyy-MM-dd";
       dob.setPromptText(pattern.toLowerCase());
       dob.setConverter(new StringConverter<LocalDate>(){
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
    public void loadCard() {
        database db=new database();
        try {
            
            db.getConnect();
            //ResultSet rs=db.execution("SELECT * FROM student");
            //while(rs.next()){
                //studentList.add(new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6), rs.getInt(7)));
                cardList=studentDAO.load();
            //}
        } catch (Exception e) {
            Logger.getLogger(cardController.class.getName());
        }
        db.disconnect();
        t_ID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        t_name.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        t_dob.setCellValueFactory(new PropertyValueFactory<>("studentDOB"));
        t_email.setCellValueFactory(new PropertyValueFactory<>("studentEmail"));
        t_class.setCellValueFactory(new PropertyValueFactory<>("studentClass"));
        tableCard.setItems(cardList);     
    }
    //        ADD student Start
    
    public void addCardBtn(ActionEvent event) throws Exception{
        try {
            Student student= new Student();
        String Name=name.getText();
        LocalDate date1= dob.getValue();
        String dob1=date1.toString();
        String  email1=email.getText();
        String class1=studentclass.getText();
        student.setStudentName(AES.encrypt(Name));
        student.setStudentDOB(dob1);
        student.setStudentEmail(AES.encrypt(email1));
        student.setStudentClass(class1);
        studentDAO.addStudent(student);
        clearALL();
        loadCard();
        } catch (NullPointerException e) {
            Alert a=new Alert(AlertType.ERROR, "Please fill all field!\nUpdate account failed!");
        a.show();
        }
    }
    public void updateCardBtn(ActionEvent event) throws Exception{
        try {
            //error
        Student student = new Student();
        int idd=Integer.parseInt(id.getText());
        String Name=name.getText();
        LocalDate date1=dob.getValue();
        String dob1=date1.toString();
        String  email1=email.getText();
        String class1=studentclass.getText();
        student.setStudentID(idd);
        student.setStudentName(AES.encrypt(Name));
        student.setStudentDOB(dob1);
        student.setStudentEmail(AES.encrypt(email1));
        student.setStudentClass(class1);
        studentDAO.editStudent(student);
        loadCard();
        } catch (NumberFormatException e) {
            Alert a=new Alert(AlertType.ERROR, "Please fill all field!\nUpdate card failed!");
            a.show();
        }
    }

//delete start
    public void removeCardBtn(ActionEvent event){
        try {
            Alert.AlertType type=Alert.AlertType.CONFIRMATION;
        Alert al=new Alert(type,"");
        al.setTitle("Confirm");
        al.setContentText("Are you sure you want to delete this?");
        Optional<ButtonType> res= al.showAndWait();
        if(res.get() == ButtonType.OK){
            int idd=Integer.parseInt(id.getText());
            studentDAO.deleteStudent(idd);
            loadCard();
        }
        } catch (NumberFormatException e) {
            Alert a=new Alert(AlertType.ERROR, "Please select a card!\nDelete card failed!");
            a.show();
        }
      
    }
//delete end    
    
    //          Add student end
    public void onSelect() {
        this.tableCard.setRowFactory(param -> {
            TableRow row = new TableRow();

            row.setOnMouseClicked(et -> {
                Student student = this.tableCard.getSelectionModel().getSelectedItem();
                this.id.setText(Integer.toString(student.getStudentID()));
                this.name.setText((student.getStudentName()));
                LocalDate dob1=LocalDate.parse(student.getStudentDOB());
                this.email.setText(student.getStudentEmail());
                this.dob.setValue(dob1);
                this.studentclass.setText(student.getStudentClass());
            });
            return row;
        });
    }

    public void clearALL(){
        id.clear();
        name.clear();
        dob.setValue(null);
        email.clear();
        studentclass.clear();
    }
    @FXML
    private JFXButton menuBtn;
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