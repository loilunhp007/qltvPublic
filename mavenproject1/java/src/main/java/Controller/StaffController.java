/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Controller.database;
import DAO.bookDAO;
import DAO.staffDAO;
import Entity.*;
import Secure.AES;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import java.text.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;

import javafx.scene.control.Alert.AlertType;
import javax.swing.JOptionPane;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.event.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Anh Quan
 */
public class StaffController implements Initializable {

    @FXML
    private TableView<Staff> staffTable;
    @FXML
    private TableColumn<Staff, Integer> ID;
    @FXML
    private TableColumn<Staff, String> nameC;
    @FXML
    private TableColumn<Staff, String> dobC;
    @FXML
    private TableColumn<Staff, String> genderC;
    @FXML
    private TableColumn<Staff, String> phoneC;
    @FXML
    private TableColumn<Staff, String> addressC;
    @FXML
    private TableColumn<Staff, String> roleC;
    @FXML
    private TableColumn<Staff, Integer> salaryC;
    @FXML
    private TextField txtID;
    @FXML
    private TextField name;
    @FXML
    private DatePicker dob;
    @FXML
    private TextField role;
    @FXML
    private TextField phone;
    @FXML
    private TextField address;
    @FXML
    private TextField salary;
    @FXML
    private Button addBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private TextField searchStaff;
    @FXML
    private RadioButton male;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton female;
    @FXML
    private RadioButton namesearch;
    @FXML
    private RadioButton idsearch;
    @FXML
    private ToggleGroup searchbar;
    @FXML
    private ImageView staffimg;
    @FXML
    private ComboBox cbox;
    @FXML
    private Button menuBtn;

    ObservableList<Staff> staffList = FXCollections.observableArrayList();
    ObservableList optional = FXCollections.observableArrayList();
    String imgURL;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillCombobox();
        loadStaff();
        onSelect();
    }

    public void convertDate() {
        String pattern = "yyyy-MM-dd";
        dob.setPromptText(pattern.toLowerCase());
        dob.setConverter(new StringConverter<LocalDate>() {
            DateTimeFormatter DTF = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate t) {
                if (t != null) {
                    return DTF.format(t);
                }
                return null;
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, DTF);
                }
                return null;
            }
        });
    }

    public void loadStaff() {
        database db = new database();
        try {
            staffList = staffDAO.load();
        } catch (Exception e) {
            Logger.getLogger(StaffController.class.getName());
        }
        db.disconnect();
        ID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        nameC.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        dobC.setCellValueFactory(new PropertyValueFactory<>("staffDOB"));
        genderC.setCellValueFactory(new PropertyValueFactory<>("staffGender"));
        phoneC.setCellValueFactory(new PropertyValueFactory<>("staffPhone"));
        addressC.setCellValueFactory(new PropertyValueFactory<>("staffAddr"));
        roleC.setCellValueFactory(new PropertyValueFactory<>("role_name"));
        salaryC.setCellValueFactory(new PropertyValueFactory<>("staffSalary"));
        staffTable.setItems(staffList);
    }
    // ADD staff Start

    public void addStaffbtn(ActionEvent event) throws Exception {
        try {
            Staff staff = new Staff();
            String Name = name.getText();
            LocalDate d1 = dob.getValue();
            String date1 = d1.toString();
            String Addr = address.getText();
            if (Addr.equals(""))
                throw new Exception();
            String gender1, rolename;
            int sal;
            String phone1;
            try {
                rolename = cbox.getSelectionModel().getSelectedItem().toString();

            } catch (Exception e) {
                Alert a = new Alert(AlertType.ERROR, "Role can't be empty!\nAdd staff failed!");
                a.show();
                return;
            }
            try {
                phone1 = phone.getText();
            } catch (Exception e) {
                Alert a = new Alert(AlertType.ERROR, "Phone must be number!\nAdd staff failed!");
                a.show();
                return;
            }
            try {
                sal = Integer.parseInt(salary.getText());
            } catch (Exception e) {
                Alert a = new Alert(AlertType.ERROR, "Salary must be number!\nAdd staff failed!");
                a.show();
                return;
            }
            int role1 = staffDAO.findRoleByName(rolename);
            if (male.isSelected() == true) {
                gender1 = "Male";
            } else {
                gender1 = "Female";
            }
            if (imgURL.equals(""))
                throw new Exception();
            staff.setStaffName(AES.encrypt(Name));
            staff.setStaffDOB(date1);
            staff.setStaffAddr(AES.encrypt(Addr));
            staff.setStaffGender(gender1);
            staff.setStaffPhone(AES.encrypt(phone1));
            staff.setStaff_role(role1);
            staff.setStaffSalary(sal);
            File file= new File(imgURL);
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            staff.setStaffImg(AES.encryptFromAES(Base64.getEncoder().encodeToString(bytes)));
            staffDAO.addStaff(staff);
            loadStaff();
        } catch (Exception e) {
            Alert a = new Alert(AlertType.ERROR,
                    "Please make sure that you have filled all the information!\nAdd staff failed!");
            a.show();
        }
    }

    public void updateStaffBtn(ActionEvent event) throws Exception {
        try {
            Staff staff = new Staff();
            int idd = Integer.parseInt(txtID.getText());
            String Name = name.getText();
            LocalDate d1 = dob.getValue();
            String dob1 = d1.toString();
            String Addr = address.getText();
            if (Addr.equals(""))
                throw new Exception();
            String gender1;
            int sal; 
            String phone1;
            if (txtID.getText().equals("")) {
                Alert a = new Alert(AlertType.ERROR, "Please choose a staff to make changes!\nUpdate staff failed!");
                a.show();
                return;
            }
            try {
                phone1 = phone.getText();
            } catch (Exception e) {
                Alert a = new Alert(AlertType.ERROR, "Phone must be number!\nAdd staff failed!");
                a.show();
                return;
            }
            try {
                sal = Integer.parseInt(salary.getText());
            } catch (Exception e) {
                Alert a = new Alert(AlertType.ERROR, "Salary must be number!\nAdd staff failed!");
                a.show();
                return;
            }
            if (male.isSelected() == true) {
                gender1 = "Male";
            } else {
                gender1 = "Female";
            }
            String role = cbox.getSelectionModel().getSelectedItem().toString();
            int role1 = staffDAO.findRoleByName(role);
            staff.setStaffID(idd);
            staff.setStaffName(AES.encrypt(Name));
            staff.setStaffDOB(dob1);
            staff.setStaffAddr(AES.encrypt(Addr));
            staff.setStaffGender(gender1);
            staff.setStaffPhone(AES.encrypt(phone1));
            staff.setStaff_role(role1);
            staff.setStaffSalary(sal);
            File file= new File(imgURL);
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            staff.setStaffImg(AES.encryptFromAES(Base64.getEncoder().encodeToString(bytes)));
            staffDAO.editStaff(staff);
            loadStaff();
        } catch (Exception e) {
            Alert a = new Alert(AlertType.ERROR,
                    "Please make sure that you have filled all the information!\nAdd staff failed!");
            a.show();
        }
    }

    // delete start
    public void rmvStaff(ActionEvent event) {
        if (txtID.getText().equals("")) {
            Alert a = new Alert(AlertType.ERROR, "Please choose a staff to make changes!\nUpdate staff failed!");
            a.show();
            return;
        }
        Alert al = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this?");
        al.setTitle("Confirm");
        Optional<ButtonType> res = al.showAndWait();
        if (res.get() == ButtonType.OK) {
            int idd = Integer.parseInt(txtID.getText());
            staffDAO.deleteStaff(idd);
            loadStaff();
        }
    }
    // delete end

    public void refreshstaff() {
        staffList.clear();
        database db = new database();
        try {

            db.getConnect();
            ResultSet rs = db.execution("SELECT * FROM Staff");
            while (rs.next()) {
                staffList.add(new Staff(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            Logger.getLogger(StaffController.class.getName());
        }
        db.disconnect();
        ID.setCellValueFactory(new PropertyValueFactory<>("staffID"));
        nameC.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        dobC.setCellValueFactory(new PropertyValueFactory<>("staffDOB"));
        genderC.setCellValueFactory(new PropertyValueFactory<>("staffGender"));
        phoneC.setCellValueFactory(new PropertyValueFactory<>("staffPhone"));
        addressC.setCellValueFactory(new PropertyValueFactory<>("staffAddr"));
        roleC.setCellValueFactory(new PropertyValueFactory<>("staff_role"));
        salaryC.setCellValueFactory(new PropertyValueFactory<>("staffSalary"));
        staffTable.setItems(staffList);
    }

    public void onSelect() {
        this.staffTable.setRowFactory(param -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                Staff s = this.staffTable.getSelectionModel().getSelectedItem();
                this.txtID.setText(Integer.toString(s.getStaffID()));
                this.name.setText((s.getStaffName()));
                String d1 = s.getStaffDOB();
                LocalDate date1 = LocalDate.parse(d1);
                this.dob.setValue(date1);
                if (s.getStaffGender().equals("Male"))
                    male.setSelected(true);
                else
                    female.setSelected(true);
                cbox.setValue(s.getRole_name());
                // this.role.setText(Integer.toString(staffDAO.findRoleByName(s.getRole_name())));
                this.phone.setText(s.getStaffPhone());
                this.address.setText(s.getStaffAddr());
                this.salary.setText(Integer.toString(s.getStaffSalary()));
                InputStream input;
                try {
                	Blob blob = s.getBlob();
                	byte[] bdata = blob.getBytes(1, (int) blob.length());
                	String ss = new String(bdata);	
                	byte[] s2 = Base64.getDecoder().decode(AES.decryptFromAES(ss));              	
                    input = new ByteArrayInputStream(s2);
                    Image bkimg=new Image(input);
                    staffimg.setImage(bkimg);
                } catch (SQLException e) {
                    return;
                }
                
                
            });
			return row;
        });
    }

    public void clearAll(){
        txtID.clear();
        name.clear();
        dob.setValue(null);
        phone.clear();
        address.clear();
        salary.clear();
    }
    public void searchBar(){
        FilteredList<Staff> flstaff = new FilteredList(staffList, p -> true);
        flstaff.removeAll();
        staffTable.setItems(flstaff);
        if (searchStaff.getText().isEmpty()) staffTable.setItems(staffList);            
        else {
            if (namesearch.isSelected()==true) 
            flstaff.setPredicate(p -> p.getStaffName().toLowerCase().contains(searchStaff.getText().toLowerCase().trim()));
            else {
                if(searchStaff.getText().matches("-?([1-9][0-9]*)?"))
                flstaff.setPredicate(p -> p.getStaffID() == Integer.parseInt(searchStaff.getText()));
                else staffTable.setItems(staffList);
            }
        }
    }

    public void fillCombobox() {
         database db=new database();
        try {
            db.getConnect();
             String sql="SELECT roleName FROM role WHERE 1";
             ResultSet rs=db.execution(sql);
            while(rs.next()) {
                optional.add(rs.getString(1));
            }
        } catch ( SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error while loading combobox");
        }
        db.disconnect();
        cbox.setItems(optional);
    }
    
    public void menuOpen() throws Exception{
        menuBtn.getScene().getWindow().hide();
         Parent root = FXMLLoader.load(getClass().getResource("../View/Function.fxml"));
                 Stage mainStage=new Stage();
                 Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }

    public void fileChooser() {
        try {
        Stage stage=new Stage();
        FileChooser fc= new FileChooser();
        fc.setTitle("Choose an image");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
        File file = fc.showOpenDialog(stage);
        imgURL= file.toString()/*.replaceAll("\\\\", "\\\\\\\\")*/;
        Image img = new Image(file.toURI().toString());
        staffimg.setImage(img);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null,"No image found");
        }
    }
}

