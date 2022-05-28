package Entity;

public class BookLending {
    private int lendID,lendStudentID,issued_by;
    private String createDay,studentName,staffName,returnDate;
    private int total;

    public BookLending() {
    }

    
    public BookLending(int lendID, int lendStudentID, String createDay, String returnDate,int issued_by,int total) {
        this.lendID = lendID;
        this.lendStudentID = lendStudentID;
        this.createDay = createDay;
        this.returnDate= returnDate;
        this.issued_by = issued_by;
        this.total= total;
    }
    public BookLending(int lendID, String studentName, String createDay,String returnDate, String staffName,int total) {
        this.lendID = lendID;
        this.studentName = studentName;
        this.createDay = createDay;
        this.returnDate= returnDate;
        this.staffName = staffName;
        this.total= total;
    }
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLendID() {
        return lendID;
    }

    public void setLendID(int lendID) {
        this.lendID = lendID;
    }

    public int getLendStudentID() {
        return lendStudentID;
    }

    public void setLendStudentID(int lendStudentID) {
        this.lendStudentID = lendStudentID;
    }

    public int getIssued_by() {
        return issued_by;
    }

    public void setIssued_by(int issued_by) {
        this.issued_by = issued_by;
    }

    public String getCreateDay() {
        return createDay;
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }
    @Override
    public String toString() {
        return "BookLending{" +
                "lendID=" + lendID +
                ", lendStudentID='" + lendStudentID + '\'' +
                ", issued_by='" + issued_by + '\'' +
                ", createDay='" + createDay + '\'' +
                ", total=" + total +
                '}';
    }

    public BookLending(int lendID) {
        this.lendID = lendID;
    }
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    
}
