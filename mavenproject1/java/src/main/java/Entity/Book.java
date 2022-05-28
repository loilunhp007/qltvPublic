package Entity;

import java.io.FileInputStream;

import com.mysql.cj.jdbc.Blob;

public class Book {
    private int bookID;
    private int bookPrice;
    private int available;
    private int bookAuthorID,bookCategoryID,bookPublisher;
    private String bookName,bookAuthor,bookCategory,bookImg;
    private java.sql.Blob bookBlob;

    public Book() {
    }

    public Book(int bookID) {
        this.bookID = bookID;
    }

    public Book(int bookID, String bookName, int bookAuthorID, int bookCategoryID,int bookPublisher,int bookPrice,int available, String bookImg) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthorID = bookAuthorID;
        this.bookCategoryID = bookCategoryID;
        this.bookPublisher = bookPublisher;
        this.bookPrice = bookPrice;
        this.available= available;
        this.bookImg = bookImg;
    }
    
    public Book(int bookID, String bookName, String bookAuthor, String bookCategory,int bookPublisher,int bookPrice,int available, String bookImg) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
        this.bookPublisher = bookPublisher;
        this.bookPrice = bookPrice;
        this.available=available;
        this.bookImg = bookImg;
    }
    
    public Book(int bookID, String bookName, String bookAuthor, String bookCategory,int bookPublisher,int bookPrice,int available, java.sql.Blob bookBlob) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
        this.bookPublisher = bookPublisher;
        this.bookPrice = bookPrice;
        this.available = available;
        this.bookBlob = bookBlob;
    }

    public java.sql.Blob getBlob(){
        return bookBlob;
    }

    public void setBlob(java.sql.Blob bookBlob){
        this.bookBlob = bookBlob;
    }

    public String getBookImg() {
        return bookImg;
    }
    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(int bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public int getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(int bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
    public int getBookAuthorID() {
        return bookAuthorID;
    }

    public void setBookAuthorID(int bookAuthorID) {
        this.bookAuthorID = bookAuthorID;
    }

    public int getBookCategoryID() {
        return bookCategoryID;
    }
    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(String bookCategory) {
        this.bookCategory = bookCategory;
    }

    public void setBookCategoryID(int bookCategoryID) {
        this.bookCategoryID = bookCategoryID;
    }


    @Override
    public String toString() {
        return "Book [ bookID=" + bookID + ", bookName=" + bookName + ", bookAuthorID=" + bookAuthorID + ", bookCategoryID=" + bookAuthorID
        + ", available=" + available + ", bookPrice=" + bookPrice + ", bookPublisher=" + bookPublisher + "]";
    }

    public Book(int bookID, int bookPrice, int available, int bookAuthorID, int bookCategoryID, String bookName,
            int bookPublisher, String bookAuthor, String bookCategory) {
        this.bookID = bookID;
        this.bookPrice = bookPrice;
        this.available = available;
        this.bookAuthorID = bookAuthorID;
        this.bookCategoryID = bookCategoryID;
        this.bookName = bookName;
        this.bookPublisher = bookPublisher;
        this.bookAuthor = bookAuthor;
        this.bookCategory = bookCategory;
    }

    

    

}
