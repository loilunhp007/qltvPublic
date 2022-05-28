package Entity;
public class Student {
    private int studentID;
    private String studentName,studentDOB,studentEmail,studentClass;

    public Student() {
    }

    public Student(int studentID) {
        this.studentID = studentID;
    }

    public Student(int studentID, String studentName, String studentDOB, String studentEmail, String studentClass) {
        this.studentID = studentID;
        this.studentName = studentName;
        this.studentDOB = studentDOB;
        this.studentEmail = studentEmail;
        this.studentClass = studentClass;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentDOB() {
        return studentDOB;
    }

    public void setStudentDOB(String studentDOB) {
        this.studentDOB = studentDOB;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studenID=" + studentID +
                ", studentName='" + studentName + '\'' +
                ", StudentDOB='" + studentDOB + '\'' +
                ", studenEmail='" + studentEmail + '\'' +
                ", StudentClass='" + studentClass + '\'' +
                '}';
    }
}
