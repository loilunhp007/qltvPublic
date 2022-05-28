/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import Controller.database;
import Entity.Staff;
import Secure.AES;
import Secure.md5Func;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anh Quan
 */

public class LoginController implements Initializable {
    @FXML
    private Button loginBtn;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private javafx.scene.control.Label warning;
    @FXML
    private JFXDialogLayout diaglog;
    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    private Pattern pattern;
    public static final String  user_Pattern="^[a-z0-9._-]{6-15}$";
    public static final String  pass_Pattern="((?=.*d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!.#$@_+,?-]).{8,20})";
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        database db=new database();
        db.getConnect();
    }    
    
    @FXML
    public void loginPressed(ActionEvent event) throws Exception {
        database db=new database();
        db.getConnect();
        try {
            Boolean check=false;
            String txtName=username.getText();
            String txtPass=password.getText();
            txtPass = md5Func.getMd5(txtPass);
            ResultSet rs=db.execution("SELECT ac.userName,ac.userPassword,ac.staffID,s.staff_roleID FROM account ac join staff s on ac.staffID=s.staffID WHERE ac.username='"+txtName+"' and ac.userPassword='"+txtPass+"';");
            while(rs.next()){
                check=true;
                AES.generatorKey(AES.k);
                FunctionController.user = new Staff();
                FunctionController.user.setStaffID(rs.getInt(3));
                FunctionController.user.setStaff_role(rs.getInt(4));
                loginBtn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("../View/Function.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
                        
                
            }
            if(check==false){
                JOptionPane.showMessageDialog(null,"Incorrect username or password");
            }
        } catch (Exception e) {
            
        }
        /*if (Connect.checkAccount(username.getText(),password.getText()) == 0) {
            ResultSet rs=db.excution("select ");
            warning.setVisible(true);
        }
        else {
            warning.setVisible(false);
        }*/
    }
    public boolean validateUser(final String userName){
        pattern=Pattern.compile(user_Pattern);
        return pattern.matcher(userName).matches();
    }
    public boolean validatePass(final String password){
        pattern=Pattern.compile(pass_Pattern);
        return pattern.matcher(password).matches();
    }
    @FXML
    public void onMouseEnter() {
        loginBtn.setStyle("-fx-background-color: #39b54a; -fx-border-color: #39b54a; -fx-text-fill: black;");
    }
    
    @FXML
    public void onMouseExit() {
        loginBtn.setStyle("-fx-background-color: #88b38f; -fx-border-color: #88b38f; -fx-text-fill: white;");
    }
    
}
