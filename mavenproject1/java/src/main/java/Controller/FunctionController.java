/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;
import Entity.Book;
import Entity.Staff;

import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;
import javafx.stage.Stage;
/**
 * FXML Controller class
 *
 * @author Anh Quan
 */
public class FunctionController implements Initializable {
	public static Staff user;
    ObservableList<Book> bookList = FXCollections.observableArrayList();
    @FXML
    private Button bookBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private ImageView staffimg;
    @FXML
    private ImageView bookimg;
    @FXML
    private ImageView logoutimg;
    @FXML
    private TextField log;
    @FXML
    private TextField a;
    @FXML
    private Button staffBtn;
    @FXML
    private Button accountBtn;
    @FXML
    private ImageView accountimg;
    @FXML
    private Button cardBtn;
    @FXML
    private ImageView cardimg;
    @FXML
    private Button lendingBtn;
    @FXML
    private ImageView lendingimg;
    @FXML
    private Button authorBtn;
    @FXML
    private ImageView authorimg;
    @FXML
    private Button reportBtn;
    @FXML
    private ImageView reportimg;
    @FXML
    private Button categoryBtn;
    @FXML
    private ImageView categoryimg;
     
    @Override
    public void initialize( URL url, ResourceBundle rb){
        //bookimg.setImage(new Image("../../resources/res/personnel.png"));

    }
    public void get_accountID(int ID){
        log.setText(Integer.toString(ID));
    }
    public void staffOpen() throws Exception{
    	if(user.getStaff_role()==2) {
    		staffBtn.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("../View/StaffManagement.fxml"));
            Stage mainStage=new Stage();
            Scene scene=new Scene(root);
            mainStage.setScene(scene);
            mainStage.show();
    	}else {
    		JOptionPane.showMessageDialog(null,"You do not have permission to access");
    	}
        
    }
    
    public void bookOpen() throws Exception{
        bookBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../View/Book.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
    
    public void accountOpen() throws Exception{
    	if(user.getStaff_role()==2) {
    		 accountBtn.getScene().getWindow().hide();
    	        Parent root = FXMLLoader.load(getClass().getResource("../View/Account.fxml"));
    	                Stage mainStage=new Stage();
    	                Scene scene=new Scene(root);
    	                mainStage.setScene(scene);
    	                mainStage.show();
    	}else {
    		JOptionPane.showMessageDialog(null,"You do not have permission to access");
    	}
       
    }
    
    public void cardOpen() throws Exception{
        cardBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../View/LibraryCard.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
    
    public void lendOpen() throws Exception{
        lendingBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../View/AdminLending.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }

    public void authorOpen() throws Exception{
        authorBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../View/Author.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
    
    public void reportOpen() throws Exception{
        reportBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../View/Export.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
    
    public void logoutOpen() throws Exception{
        logoutBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../View/Login.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }
    
    public void categoryOpen() throws Exception{
        categoryBtn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("../View/Category.fxml"));
                Stage mainStage=new Stage();
                Scene scene=new Scene(root);
                mainStage.setScene(scene);
                mainStage.show();
    }  
}