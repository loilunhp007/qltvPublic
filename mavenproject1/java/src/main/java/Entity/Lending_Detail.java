package Entity;

public class Lending_Detail {
    private int lendID,bookID;
    private String dueDay,lendStatus,bookName;

    public Lending_Detail() {
    }

    public Lending_Detail(int lendID,int bookID) {
        this.lendID = lendID;
        this.bookID =bookID;
    }

    public Lending_Detail(int lendID, int bookID ,String dueDay, String lendStatus) {
        this.lendID = lendID;
        this.bookID = bookID;
        this.dueDay = dueDay;
        this.lendStatus = lendStatus;
    }
    public Lending_Detail(int lendID, String bookName ,String dueDay, String lendStatus) {
        this.lendID = lendID;
        this.bookName = bookName;
        this.dueDay = dueDay;
        this.lendStatus = lendStatus;
    }

    public int getLendID() {
        return lendID;
    }

    public void setLendID(int lendID) {
        this.lendID = lendID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getDueDay() {
        return dueDay;
    }

    public void setDueDay(String dueDay) {
        this.dueDay = dueDay;
    }

    public String getLendStatus() {
        return lendStatus;
    }

    public void setLendStatus(String lendStatus) {
        this.lendStatus = lendStatus;
    }

    @Override
    public String toString() {
        return "Lending_Detail{" +
                "lendID=" + lendID +
                ", bookID=" + bookID +
                ", dueDay='" + dueDay + '\'' +
                ", lendStatus='" + lendStatus + '\'' +
                '}';
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    
}
