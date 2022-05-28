/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.Observable;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.scene.control.Alert.*;
import javafx.scene.control.*;

import DAO.categoryDAO;
import Entity.Categories;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lapgo
 */
public class CategoryController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField name;
    @FXML
    private Button addBtn;
    @FXML
    private Button removeBtn;
    @FXML
    private Button update;
    @FXML
    private Button clear;
    @FXML
    private TableView<Categories> tableCate;
    @FXML
    private TableColumn<Categories, Integer> t_id;
    @FXML
    private TableColumn<Categories, String> t_name;
    @FXML
    private RadioButton idsearch;
    @FXML
    private ToggleGroup searchbar;
    @FXML
    private RadioButton namesearch;
    @FXML
    private TextField searchAuthor;

    ObservableList<Categories> l_cate =FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCate();
        onSelect();
    }    
    @FXML
    public void loadCate(){
        database db=new database();
        try{
            db.getConnect();
            l_cate=categoryDAO.load();

        }catch(Exception e){
            Logger.getLogger(CategoryController.class.getName());
        }finally{}
        t_id.setCellValueFactory(new PropertyValueFactory<>("categoryID"));
        t_name.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        tableCate.setItems(l_cate);
    }
    //        ADD student Start
    
    public void addCateBtn(ActionEvent event) throws Exception{
        try {
            Categories cate= new Categories();
        String Name=name.getText();
        if(Name.equals("")){
            Alert a=new Alert(AlertType.ERROR, "Please fill all field!\nAdd category failed!");
            a.show();
        }
        else{
            cate.setCategoryName(Name);
            categoryDAO.addcategories(cate);
            clearALL();
            loadCate();
        }
        } catch (Exception e) {   
        }
    }
   
    public void updateCateBtn(ActionEvent event) throws Exception{
        try {
            //error
        Categories cate= new Categories();
        int idd=Integer.parseInt(id.getText());
        String Name=name.getText();
        cate.setCategoryID(idd);
        cate.setCategoryName(Name);
        categoryDAO.editcategories(cate);
        loadCate();
        } catch (NumberFormatException e) {
            Alert a=new Alert(AlertType.ERROR, "Please choose Category to update!\nUpdate author failed!");
        a.show();
        }
    }

//delete start
    public void removeCateBtn(ActionEvent event){
        try {
            Alert.AlertType type=Alert.AlertType.CONFIRMATION;
        Alert al=new Alert(type,"");
        al.setTitle("Confirm");
        al.setContentText("Are you sure you want to delete this?");
        Optional<ButtonType> res= al.showAndWait();
        if(res.get() == ButtonType.OK){
            int idd=Integer.parseInt(id.getText());
            categoryDAO.deletecate(idd);
            loadCate();
        }
      
        } catch (NumberFormatException e) {
            Alert a=new Alert(AlertType.ERROR, "Please choose Category to remove!\nRemove author failed!");
        a.show();
        }
    }
//delete end    
    
    //          Add student end
    public void onSelect() {
        this.tableCate.setRowFactory(param -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                Categories cate = this.tableCate.getSelectionModel().getSelectedItem();
                this.id.setText(Integer.toString(cate.getCategoryID()));
                this.name.setText((cate.getCategoryName()));
            });
            return row;
        });
    }

    public void clearALL(){
        id.clear();
        name.clear();
    }

    public void searchBar(){
        FilteredList<Categories> flaccount = new FilteredList(l_cate, p -> true);
        flaccount.removeAll();
        tableCate.setItems(flaccount);
        if (searchAuthor.getText().isEmpty()) tableCate.setItems(l_cate);        
        else {
            if (namesearch.isSelected()==true) 
            flaccount.setPredicate(p -> p.getCategoryName().toLowerCase().contains(searchAuthor.getText().toLowerCase().trim()));
            else {
                if(searchAuthor.getText().matches("[1-9]*")) 
                flaccount.setPredicate(p -> p.getCategoryID() == Integer.parseInt(searchAuthor.getText()));
                else tableCate.setItems(l_cate);
            }
        }
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
