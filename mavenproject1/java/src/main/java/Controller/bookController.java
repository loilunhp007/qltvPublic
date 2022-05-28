package Controller;

import java.net.URL;
import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import javax.sql.rowset.serial.SerialBlob;
import javax.swing.ImageIcon;

import Controller.database;
import DAO.authorDAO;
import DAO.bookDAO;
import DAO.categoryDAO;
import Entity.*;
import Secure.AES;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.control.cell.*;
import javafx.scene.image.*;
import javafx.event.*;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType; 
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class bookController implements Initializable {
    @FXML
    private TableView<Book> bookTable;
    @FXML
    private TableColumn<Book, Integer> bookID;
    @FXML
    private TableColumn<Book, String> bookName;
    @FXML
    private TableColumn<Book, String> bookAuthorID;
    @FXML
    private TableColumn<Book, String> bookCategoryID;
    @FXML
    private TableColumn<Book, String> bookPub;
    @FXML
    private TableColumn<Book, Integer> bookQuantity;
    @FXML
    private TableColumn<Book, Integer> bookPrice;
    @FXML
    private Button bookAdd;
    @FXML
    private RadioButton namesearch;
    @FXML
    private RadioButton idsearch;
    @FXML
    private ToggleGroup searchbar;
    @FXML
    private Button bookUpdate;
    @FXML
    private Button bookRemove;
    @FXML
    private Button bookRefresh;
    @FXML
    private TextField id;
    @FXML
    private TextField book;
    @FXML
    private ComboBox author;
    @FXML
    private ComboBox category;
    @FXML
    private TextField publisher;
    @FXML
    private TextField price;
    @FXML
    private TextField available;
    @FXML
    private TextField bookSearch;
    @FXML
    private ImageView bookimg;
    /**
     * Initializes the controller class.
     */
    ObservableList<Book> bookList = FXCollections.observableArrayList();
    ObservableList optional = FXCollections.observableArrayList();
    ObservableList optional1 = FXCollections.observableArrayList();
    String imgURL;
    public static final int keyA = 5;
    public static final int keyB = 7;
    public static AES aes;
    @Override
    public void initialize( URL url, ResourceBundle rb){
        fillCombobox();
        aes = new AES();
        loadBook();
        onSelect();
    }
//Show data book from database
    public void loadBook() {
        database db=new database();
        bookList.clear();
        try {
            db.getConnect();
            //ResultSet rs=db.execution("SELECT b.bookID,b.bookName,a.authorName,c.categoryName,b.bookPublisher,b.bookprice,b.bookPages FROM book b join Author a on b.bookAuthorID=a.authorID join Categories c on b.bookCategoryID=c.categoryID WHERE 1;");
            //ResultSet rs=db.execution("SELECT * FROM book");
            //while(rs.next()){
                //bookList.add(new Book(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getInt(6), rs.getInt(7)));
                bookList=bookDAO.load();
            //}
        } catch (Exception e) {
            Logger.getLogger(bookController.class.getName());
        }
        db.disconnect();
        
        bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        bookName.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        bookAuthorID.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        bookCategoryID.setCellValueFactory(new PropertyValueFactory<>("bookCategory"));
        bookPub.setCellValueFactory(new PropertyValueFactory<>("bookPublisher"));
        bookPrice.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        bookQuantity.setCellValueFactory(new PropertyValueFactory<>("available"));
        bookTable.setItems(bookList);     
    }
    
    public void fillCombobox() {
        database db=new database();
        db.getConnect();
        try {
            	
            String sql="SELECT authorName FROM author WHERE 1";
            ResultSet rs=db.execution(sql);
            while(rs.next()) {
                optional.add(aes.decrypt(rs.getString(1)));
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        author.setItems(optional);
        try {
            String sql="SELECT categoryName FROM categories";
            ResultSet rs=db.execution(sql);
            while(rs.next()) {
                optional1.add(rs.getString(1));
            }
        } catch ( SQLException e) {
            e.printStackTrace();
        }
        db.disconnect();
        category.setItems(optional1);
   }

    public void addBookbtn(ActionEvent event) throws SQLException{
        try {
            Book book1= new Book();
            String Name=book.getText();
            int publish=Integer.parseInt(publisher.getText());
            int cpri, avai;
            String Author, Category;
            if (Name.equals("")) throw new Exception();
            if (publish == 0) throw new Exception();        
            try {
                Author=author.getSelectionModel().getSelectedItem().toString();
                
            } catch (Exception e) {
                Alert a=new Alert(AlertType.ERROR, "Author can't be empty!\nAdd book failed!");
                a.show();
                return;
            }
            try {
                Category=category.getSelectionModel().getSelectedItem().toString();
                
            } catch (Exception e) {
                Alert a=new Alert(AlertType.ERROR, "Category can't be empty!\nAdd book failed!");
                a.show();
                return;
            }
            try {
                cpri=Integer.parseInt(price.getText());
            } catch(Exception e) {
                Alert a=new Alert(AlertType.ERROR, "Price must be number!\nAdd book failed!");
                a.show();
                return;
            }
            try {
                avai= Integer.parseInt(available.getText());
            } catch(Exception e) {
                Alert a=new Alert(AlertType.ERROR, "The number of available books must be number!\nAdd book failed!");
                a.show();
                return;
            }
            int authorid= authorDAO.findRoleByName(aes.encrypt(author.getSelectionModel().getSelectedItem().toString()));
            int categoryid= categoryDAO.findRoleByName(category.getSelectionModel().getSelectedItem().toString());
            book1.setBookName(AES.encrypt(Name));
            book1.setBookAuthorID(authorid);
            book1.setBookCategoryID(categoryid);
            book1.setBookPublisher(publish);
            book1.setBookPrice(cpri);
            book1.setAvailable(avai);
            File file= new File(imgURL);
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            book1.setBookImg(AES.encryptFromAES(Base64.getEncoder().encodeToString(bytes)));
            bookDAO.addBook(book1);
            loadBook();
        } catch (Exception e) {
        	e.printStackTrace();
            Alert a=new Alert(AlertType.ERROR, "Please make sure that you have filled all the information!\nAdd book failed!");
            a.show();
        }
    }
    public void updateBookBtn(ActionEvent event) throws Exception{
        try {
            Book book1 = new Book();
            String Name=book.getText();
            String Author, Category;
            int publish=Integer.parseInt(publisher.getText());
            int cpri=Integer.parseInt(price.getText());
            int avai=Integer.parseInt(available.getText());
            int idd;
            if (id.getText().equals("")) {
                Alert a= new Alert (AlertType.ERROR,"Please choose a staff to make changes!\nUpdate staff failed!");
                a.show();
                return;
            }
            else {
                idd=Integer.parseInt(id.getText());
            }
            if (Name.equals("")) throw new Exception();
            if (publish == 0 ) throw new Exception(); 
            try {
                Author=author.getSelectionModel().getSelectedItem().toString();
                
            } catch (Exception e) {
                Alert a=new Alert(AlertType.ERROR, "Author can't be empty!\nAdd book failed!");
                a.show();
                return;
            }
            try {
                Category=category.getSelectionModel().getSelectedItem().toString();
                
            } catch (Exception e) {
                Alert a=new Alert(AlertType.ERROR, "Category can't be empty!\nAdd book failed!");
                a.show();
                return;
            }
            try {
                cpri=Integer.parseInt(price.getText());
            } catch(Exception e) {
                Alert a=new Alert(AlertType.ERROR, "Price must be number!\nAdd book failed!");
                a.show();
                return;
            }
            try {
                avai= Integer.parseInt(available.getText());
            } catch(Exception e) {
                Alert a=new Alert(AlertType.ERROR, "The number of available books must be number!\nAdd book failed!");
                a.show();
                return;
            }
            int authorid= authorDAO.findRoleByName(aes.encrypt(author.getSelectionModel().getSelectedItem().toString()));
            int categoryid= categoryDAO.findRoleByName(category.getSelectionModel().getSelectedItem().toString());
            book1.setBookID(idd);
            book1.setBookName(AES.encrypt(Name));
            book1.setBookAuthorID(authorid);
            book1.setBookCategoryID(categoryid);
            book1.setBookPublisher(publish);
            book1.setBookPrice(cpri);
            book1.setAvailable(avai);
            File file= new File(imgURL);
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            book1.setBookImg(AES.encryptFromAES(Base64.getEncoder().encodeToString(bytes)));
            System.out.println(book1);
            System.out.println(book1.getBookCategoryID());
            bookDAO.editBook(book1);
            loadBook();
        } catch (Exception e) {
            Alert a=new Alert(AlertType.ERROR, "Please make sure that you have filled all the information!\nAdd book failed!");
            a.show();
        }
    }

//delete start
    public void rmv(ActionEvent event){
        Alert.AlertType type=Alert.AlertType.CONFIRMATION;
        Alert al=new Alert(type,"");
        al.setTitle("Confirm");
        al.setContentText("Are you sure you want to delete this?");
        Optional<ButtonType> res= al.showAndWait();
        if(res.get() == ButtonType.OK){
            int idd=Integer.parseInt(id.getText());
            bookDAO.deleteBook(idd);
            loadBook();
        }
      
    }
//delete end

public void refreshBook(){
    clearAll();
    loadBook();
}
//refresh end


//select row from tableView
    public void onSelect() {
        this.bookTable.setRowFactory(param -> {
            TableRow row = new TableRow();
            row.setOnMouseClicked(et -> {
                Book b = this.bookTable.getSelectionModel().getSelectedItem();
                this.id.setText(Integer.toString(b.getBookID()));
                this.book.setText((b.getBookName()));
                author.setValue(b.getBookAuthor());
                category.setValue(b.getBookCategory());
                this.publisher.setText(Integer.toString(b.getBookPublisher()));
                this.available.setText(Integer.toString(b.getAvailable()));
                this.price.setText(Integer.toString(b.getBookPrice()));
                InputStream input;
                try {
                	Blob blob = b.getBlob();
                	byte[] bdata = blob.getBytes(1, (int) blob.length());
                	String s = new String(bdata);
                	
                	byte[] s2 = Base64.getDecoder().decode(AES.decryptFromAES(s));
                	
                    input = new ByteArrayInputStream(s2);
                    File targetFile = new File("src/test/targetFile.jpg");
                    OutputStream outStream = new FileOutputStream(targetFile);
                    outStream.write(s2);
                    Image bkimg=new Image(input);
                    bookimg.setImage(bkimg);
                   /* if(bkimg !=null){
                    	
                        bookimg.setImage(bkimg);
                    }
                    else{
                        bookimg.setImage(null);
                    }*/
                } catch (SQLException e) {
                    return;
                }catch(NullPointerException ex){
                    return;
                } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            });
            return row;
        });
    }
    public void clearAll(){
        id.clear();
        book.clear();
        author.setValue(null);;
        category.setValue(null);
        publisher.clear();
        available.clear();
        price.clear();
        bookimg.setImage(null);
    }
    public void searchBar(){
        FilteredList<Book> flbook = new FilteredList(bookList, p -> true);
        flbook.removeAll();
        bookTable.setItems(flbook);
        if (bookSearch.getText().isEmpty()) bookTable.setItems(bookList);            
        else {
            if (namesearch.isSelected()==true) 
            flbook.setPredicate(p -> p.getBookName().toLowerCase().contains(bookSearch.getText().toLowerCase().trim()));
            else {
                if(bookSearch.getText().matches("-?([1-9][0-9]*)?")) 
                flbook.setPredicate(p -> p.getBookID() == Integer.parseInt(bookSearch.getText()));
                else bookTable.setItems(bookList);
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

    public void fileChooser() {
        try {
            Stage stage=new Stage();
        FileChooser fc= new FileChooser();
        fc.setTitle("Choose an image");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
        File file = fc.showOpenDialog(stage);
        imgURL= file.toString()/*.replaceAll("\\\\", "\\\\\\\\")*/;
        Image img = new Image(file.toURI().toString());
        bookimg.setImage(img);
        } catch (NullPointerException e) {
            Alert a=new Alert(AlertType.ERROR, "Image Selected fail");
            a.show();
        }
    }
}