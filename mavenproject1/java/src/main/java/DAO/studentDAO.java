package DAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.swing.JOptionPane;
import Controller.database;
import Entity.Student;
import Secure.AES;
import javafx.scene.control.*;
import javafx.collections.*;
public class studentDAO {
    public static ObservableList<Student> load(){
        ObservableList<Student> l_student=FXCollections.observableArrayList();
        database db=new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT * FROM student;");
        try {
            while(rs.next()){
                Student student = new Student(rs.getInt(1));
                student.setStudentName(AES.decrypt(rs.getString(2)));
                student.setStudentDOB(rs.getString(3));
                student.setStudentEmail(AES.decrypt(rs.getString(4)));
                student.setStudentClass(rs.getString(5));
                l_student.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Error while loading Student");
        }
        db.disconnect();
        return l_student;
    }
    public static void addStudent(Student student){
        database db = new database();
        db.getConnect();
        try{
        String sql = "INSERT INTO student (studentName,studentDOB,studentEmail,studentClass) VALUES ('";
        sql +=student.getStudentName()+"','";
        sql += student.getStudentDOB() +"','";
        sql += student.getStudentEmail() +"','";
        sql +=student.getStudentClass()+"');";
        db.update(sql);
        }catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }db.disconnect();
    }
    public static void deleteStudent(int studentID){
        database db = new database();
        db.getConnect();
        try{
        db.update("DELETE FROM Student WHERE StudentID="+studentID);
        }catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        db.disconnect();
    }
    public static void editStudent(Student student){
        database db=new database();
        db.getConnect();
        try {
            String sql="UPDATE student SET ";
        sql+="studentName='"+ student.getStudentName()+"',";
        sql+="studentDOB='"+ student.getStudentDOB()+"',";
        sql+="studentEmail='"+ student.getStudentEmail()+"',";
        sql+="studentClass='"+ student.getStudentClass()+"' WHERE StudentID="+student.getStudentID()+";";
        db.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }        
        db.disconnect();
    }
    public static Student findStudentByID(int studentID){
        database db = new database();
        db.getConnect();
        ResultSet rs = db.execution("SELECT * From student WHERE studentID="+studentID);
        try {
            while(rs.next()){
                Student student = new Student(rs.getInt(1));
                student.setStudentName(AES.decrypt(rs.getString(2)));
                student.setStudentDOB(rs.getString(3));
                student.setStudentEmail(AES.decrypt(rs.getString(4)));
                student.setStudentClass(rs.getString(5));
                return student;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Can't find anything about this");
        }
        db.disconnect();
        return null;
    }
    
}