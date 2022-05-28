
package Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import DAO.AccountDAO;
import Entity.Account;
import Secure.AES;
import Secure.md5Func;

import com.jfoenix.controls.JFXButton;
import com.mysql.cj.result.LocalDateValueFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.util.StringConverter;
import javafx.collections.*;
import javafx.scene.image.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType; 


/**
 * FXML Controller class
 *
 * @achor lapgo
 */
public class AccountController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField userName;
    @FXML
    private TextField userPass;
    @FXML
    private DatePicker createday;
    @FXML
    private DatePicker outday;
    @FXML
    private ComboBox staffID;
    @FXML
    private Button addBtn;
    @FXML
    private JFXButton menuBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button update;
    @FXML
    private Button clear;
    @FXML
    private TableView<Account> tableAccount;
    @FXML
    private TableColumn<Account, Integer> t_id;
    @FXML
    private TableColumn<Account, String> t_name;
    @FXML
    private TableColumn<Account, String> t_pass;
    @FXML
    private TableColumn<Account, String> t_startdate;
    @FXML
    private TableColumn<Account, String> t_outdate;
    @FXML
    private TableColumn<Account, String> t_staffName;
    @FXML
    private RadioButton namesearch;
    @FXML
    private RadioButton idsearch;
    @FXML
    private ToggleGroup searchbar;
    @FXML
    private TextField searchAccount;
    /**
     * Initializes the controller class.
     */
    ObservableList<Account> l_account= FXCollections.observableArrayList();
    ObservableList optional = FXCollections.observableArrayList();


    @Override
    public void initialize( URL url, ResourceBundle rb){
        convertDate();
        convertDate1();
        loadAccount();
        onSelect();
        fillCombobox();
    }

    public void fillCombobox() {
        database db=new database();
       try {
           db.getConnect();
            String sql="SELECT s.staffName FROM staff s join account a on s.staffID = a.staffID WHERE 1";
            ResultSet rs=db.execution(sql);
           while(rs.next()) {
        	   if(!optional.contains(AES.decrypt(rs.getString(1)))) {
        		   optional.add(AES.decrypt(rs.getString(1)));
        	   }
               
           }
       } catch ( SQLException e) {
           //e.printStackTrace();
           JOptionPane.showMessageDialog(null,"Error while loading combobox");
       }
       db.disconnect();
       staffID.setItems(optional);
   }
   
    public void convertDate(){
       String pattern="yyyy-MM-dd";
       createday.setPromptText(pattern.toLowerCase());
       createday.setConverter(new StringConverter<LocalDate>(){
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
    public void convertDate1(){
        String pattern="yyyy-MM-dd";
        outday.setPromptText(pattern.toLowerCase());
        outday.setConverter(new StringConverter<LocalDate>(){
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
    public void loadAccount() {
        database db=new database();
        try {
            l_account=AccountDAO.loadAccount();
        } catch (Exception e) {
            Logger.getLogger(cardController.class.getName());
        }
        db.disconnect();
        t_id.setCellValueFactory(new PropertyValueFactory<>("userID"));
        t_name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        t_pass.setCellValueFactory(new PropertyValueFactory<>("userPassword"));
        t_startdate.setCellValueFactory(new PropertyValueFactory<>("createday"));
        t_outdate.setCellValueFactory(new PropertyValueFactory<>("outofday"));
        t_staffName.setCellValueFactory(new PropertyValueFactory<>("staffName"));
        tableAccount.setItems(l_account);     
        System.out.println(l_account);
    }
    //        ADD student Start
    
    public void addAccountBtn(ActionEvent event) throws Exception{
        try{
            Account ac= new Account();
            String Name=userName.getText();
            String Pass=userPass.getText();
            Pass = md5Func.getMd5(Pass);
            LocalDate date1= LocalDate.now();
            LocalDate date2= outday.getValue();
            String dob1=date1.toString();
            String dob2=date2.toString();
            int staffidd;
            if (Name.equals("")) throw new Exception();
            if (Pass.equals("")) throw new Exception();
            try {
                staffidd=AccountDAO.findIDByName(staffID.getSelectionModel().getSelectedItem().toString());
            } catch(Exception e) {
                Alert a=new Alert(AlertType.ERROR, "Staff ID must be number!\nAdd account failed!");
                a.show();
                return;
            }
            ac.setUserName(Name);
            ac.setUserPassword(Pass);
            ac.setCreateday(dob1);
            ac.setOutofday(dob2);
            ac.setStaffID(staffidd);
            AccountDAO.addAccount(ac);
        } catch(Exception e){
            Alert a=new Alert(AlertType.ERROR, "Please make sure that you have filled all the information!\nAdd account failed!");
            a.show();
        }
        clearALL();
        loadAccount();
    }

    public void updateAccountBtn(ActionEvent event) throws Exception{
        try{
        Account ac = new Account();
        int idd;
        try {
            idd=Integer.parseInt(id.getText());           
        }
        catch (Exception e) {
            Alert a= new Alert (AlertType.ERROR,"Please choose a staff to make changes!\nUpdate staff failed!");
            a.show();
            return;   
        }
        String username=userName.getText();
        String userpass=userPass.getText();
        userpass = md5Func.getMd5(userpass);
        int staffidd=AccountDAO.findIDByName(staffID.getSelectionModel().getSelectedItem().toString());
        System.out.println(staffidd);
        LocalDate date1=outday.getValue();
        String dob1=date1.toString();
        LocalDate date2=createday.getValue();
        String dob2=date2.toString();
        
        if (username.equals("")) throw new Exception();
        if (userpass.equals("")) throw new Exception();
        ac.setUserID(idd);
        ac.setUserName(username);
        ac.setUserPassword(userpass);
        ac.setCreateday(dob2);
        ac.setOutofday(dob1);
        ac.setStaffID(staffidd);
        AccountDAO.editAccount(ac);
        } catch(Exception e) {
            Alert a=new Alert(AlertType.ERROR, "Please make sure that you have filled all the information!\nUpdate account failed!");
            a.show();
        }
        loadAccount();
    }

//delete start
    public void removeAccountBtn(ActionEvent event){
        try{
            Alert.AlertType type=Alert.AlertType.CONFIRMATION;
            Alert al=new Alert(type,"");
            al.setTitle("Confirm");
            al.setContentText("Are you sure you want to delete this?");
            Optional<ButtonType> res= al.showAndWait();
            if(res.get() == ButtonType.OK){
                int idd=Integer.parseInt(id.getText());
                AccountDAO.deleteAccount(idd);
                loadAccount();
            }}
        catch(NumberFormatException e){Alert a=new Alert(AlertType.ERROR, "Please choose account to delete!\nUpdate account failed!");
        a.show();}
    }
//delete end    
    
    //          Add student end
    public void onSelect() {
        this.tableAccount.setRowFactory(param -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                Account ac = this.tableAccount.getSelectionModel().getSelectedItem();
                this.id.setText(Integer.toString(ac.getUserID()));
                this.userName.setText(ac.getUserName());
                //this.userPass.setText(ac.getUserPassword());
                LocalDate dob1=LocalDate.parse(ac.getOutofday());
                LocalDate dob2=LocalDate.parse(ac.getCreateday());
                this.outday.setValue(dob1);
                this.createday.setValue(dob2);
                staffID.setValue(ac.getStaffName());
            });
            return row;
        });
    }

    public void clearALL(){
        id.clear();
        userName.clear();
        outday.setValue(null);
        userPass.clear();
        staffID.setValue(null);
    }

    public void searchBar(){
        FilteredList<Account> flaccount = new FilteredList(l_account, p -> true);
        flaccount.removeAll();
        tableAccount.setItems(flaccount);
        if (searchAccount.getText().isEmpty()) tableAccount.setItems(l_account);        
        else {
            if (namesearch.isSelected()==true) 
            flaccount.setPredicate(p -> p.getUserName().toLowerCase().contains(searchAccount.getText().toLowerCase().trim()));
            else {
                if(searchAccount.getText().matches("-?([1-9][0-9]*)?")) 
                flaccount.setPredicate(p -> p.getUserID() == Integer.parseInt(searchAccount.getText()));
                else tableAccount.setItems(l_account);
            }
        }
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
