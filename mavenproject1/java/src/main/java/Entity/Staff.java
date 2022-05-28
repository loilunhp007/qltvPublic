package Entity;

import java.io.FileInputStream;

import com.mysql.cj.jdbc.Blob;

public class Staff {
    private String staffName,staffdob,staffAddr,staffGender, role_name, staffImg,staffPhone;
    private int staffID,staff_role, staffSalary ;
    private java.sql.Blob staffBlob;
    public Staff() {
    }

    public Staff(int staffID) {
        this.staffID = staffID;
    }

    public Staff(int staffID, String staffName, String staffDOB, String staffAddr,String staffGender,String staffPhone, int staff_role, int staffSalary, String staffImg) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.staffdob = staffDOB;
        this.staffAddr = staffAddr;
        this.staffGender = staffGender;
        this.staffPhone = staffPhone;
        this.staff_role = staff_role;
        this.staffSalary = staffSalary;
        this.staffImg = staffImg;
    }
    public Staff(int staffID, String staffName, String staffDOB, String staffAddr,String staffGender,String staffPhone, String role_name, int staffSalary, String staffImg) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.staffdob = staffDOB;
        this.staffAddr = staffAddr;
        this.staffGender = staffGender;
        this.staffPhone = staffPhone;
        this.role_name = role_name;
        this.staffSalary=staffSalary;
        this.staffImg = staffImg;
    }
    public Staff(int staffID, String staffName, String staffDOB, String staffAddr,String staffGender,String staffPhone, int staff_role, int staffSalary, java.sql.Blob staffBlob) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.staffdob = staffDOB;
        this.staffAddr = staffAddr;
        this.staffGender = staffGender;
        this.staffPhone = staffPhone;
        this.staff_role = staff_role;
        this.staffSalary = staffSalary;
        this.staffBlob = staffBlob;
    }

    public java.sql.Blob getBlob(){
        return staffBlob;
    }

    public void setBlob(java.sql.Blob staffBlob){
        this.staffBlob=staffBlob;
    }

    public String getStaffImg() {
        return staffImg;
    }
    public void setStaffImg(String staffImg) {
        this.staffImg = staffImg;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public int getStaff_role() {
        return staff_role;
    }

    public void setStaff_role(int staff_role) {
        this.staff_role = staff_role;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffDOB() {
        return staffdob;
    }

    public void setStaffDOB(String staffdob) {
        this.staffdob = staffdob;
    }
    public String getStaffAddr() {
        return staffAddr;
    }

    public void setStaffAddr(String staffAddr) {
        this.staffAddr = staffAddr;
    }

    public String getStaffGender() {
        return staffGender;
    }

    public void setStaffGender(String staffGender) {
        this.staffGender = staffGender;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    @Override
    public String toString() {
        return "Staff{" + "staffID='" + staffID + '\''
                +
                ", staffName='" + staffName + '\'' +
                ", staffDOB='" + staffdob + '\'' +
                ", staffAddr='" + staffAddr + '\'' +
                ", staffGender='" + staffGender + '\'' +
                ", staffPhone=" + staffPhone +
                ", staff_role=" + staff_role +
                ", staffSalary=" + staffSalary +
                '}';
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Staff(int staff_role,String role_name) {
        this.role_name = role_name;
        this.staff_role = staff_role;
    }
    public int getStaffSalary() {
        return staffSalary;
    }

    public void setStaffSalary(int staffSalary) {
        this.staffSalary = staffSalary;
    }
}
