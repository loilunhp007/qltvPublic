package Entity;

public class Account {
    private int userID;
    private int staffID;
    private String userName;
    private String userPassword;
    private String staffName;
    private String createday,outofday;
    public Account() {
    }

    public Account(int userID) {
        this.userID=userID;
    }
    public int getStaffID(){
        return staffID;
    }
    public void setStaffID(int staffID){
        this.staffID =staffID;
    }
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getCreateday(){
        return createday;
    }
    public String getOutofday(){
        return outofday;
    }
    public void setCreateday(String createday){
        this.createday=createday;
    }
    /**
     * @param outofday the outofday to set
     */
    public void setOutofday(String outofday) {
        this.outofday = outofday;
    }
    @Override
    public String toString() {
        return "Account{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", createday='"+ createday + '\'' +
                ",outofday='" + outofday + '\'' +
                ",staffID='" + staffID +
                '}';
    }

    public Account(int userID, String userName, String userPassword, String createday, String outofday,int staffID) {
        this.userID = userID;
        this.staffID = staffID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createday = createday;
        this.outofday = outofday;
    }
    
    public Account(int userID, String userName, String userPassword, String createday, String outofday,String staffName) {
        this.userID = userID;
        this.staffName = staffName;
        this.userName = userName;
        this.userPassword = userPassword;
        this.createday = createday;
        this.outofday = outofday;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Account(int userID, int staffID, String userName, String userPassword, String staffName, String createday,
            String outofday) {
        this.userID = userID;
        this.staffID = staffID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.staffName = staffName;
        this.createday = createday;
        this.outofday = outofday;
    }
    
}
