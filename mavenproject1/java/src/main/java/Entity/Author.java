package Entity;

public class Author {
    private  int authorID;
    private String authorName,authorGender,authorDOB,authorEmail;

    public Author() {
    }

    public Author(int authorID) {
        this.authorID = authorID;
    }

    public Author(int authorID, String authorName, String authorGender, String authorDOB, String authorEmail) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.authorGender = authorGender;
        this.authorDOB = authorDOB;
        this.authorEmail = authorEmail;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorGender() {
        return authorGender;
    }

    public void setAuthorGender(String authorGender) {
        this.authorGender = authorGender;
    }

    public String getAuthorDOB() {
        return authorDOB;
    }

    public void setAuthorDOB(String authorDOB) {
        this.authorDOB = authorDOB;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorID=" + authorID +
                ", authorName='" + authorName + '\'' +
                ", authorGender='" + authorGender + '\'' +
                ", authorDOB='" + authorDOB + '\'' +
                ", authorEmail='" + authorEmail + '\'' +
                '}';
    }
}
