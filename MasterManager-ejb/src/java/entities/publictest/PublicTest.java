package entities.publictest;

import entities.Document;
import entities.users.Student;
import entities.users.Teacher;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "PUBLIC_TESTS")
@NamedQuery(name = "getAllPublicTests",
        query = "SELECT s FROM PublicTest s ORDER BY s.code")
public class PublicTest implements Serializable {

    @Id
    private int code;

    @NotNull
    private String title;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "TEST_DATETIME")
    private Date testDateTime;

    @NotNull
    private String place;

    @NotNull
    private String link;

    @ManyToOne
    @JoinColumn(name = "JURYPRESI_USERNAME")
    @NotNull
    private Teacher juryPresident;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "ADVISOR_USERNAME")
    private Teacher advisor;

    @NotNull
    private String outsideTeacherName;

    @NotNull
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            + "[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            + "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
            message = "Invalid email format")
    private String outsideTeacherEmail;

    @NotNull
    @OneToOne(mappedBy = "publicTest")
    private Student student;

    @OneToOne
    @JoinColumn(name = "FILE_ID")
    private Document fileRecord;

    public PublicTest() {

    }

    public PublicTest(int code, String title, Date testDateTime,
            String place, String link, Teacher juryPresident,
            Teacher advisor, String outsideTeacherName, String outsideTeacherEmail,
            Student student) {
        this.code = code;
        this.title = title;
        this.testDateTime = testDateTime;
        this.place = place;
        this.link = link;
        this.juryPresident = juryPresident;
        this.advisor = advisor;
        this.outsideTeacherEmail = outsideTeacherEmail;
        this.outsideTeacherName = outsideTeacherName;
        this.student = student;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTestDateTime() {
        return testDateTime;
    }

    public void setTestDateTime(Date testDateTime) {
        this.testDateTime = testDateTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Teacher getJuryPresident() {
        return juryPresident;
    }

    public void setJuryPresident(Teacher juryPresident) {
        this.juryPresident = juryPresident;
    }

    public Document getFileRecord() {
        if (this.fileRecord == null) {
            return new Document();
        }
        return fileRecord;
    }

    public void setFileRecord(Document fileRecord) {
        this.fileRecord = fileRecord;
    }

    public Teacher getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Teacher advisor) {
        this.advisor = advisor;
    }

    public String getOutsideTeacherName() {
        return outsideTeacherName;
    }

    public void setOutsideTeacherName(String outsideTeacherName) {
        this.outsideTeacherName = outsideTeacherName;
    }

    public String getOutsideTeacherEmail() {
        return outsideTeacherEmail;
    }

    public void setOutsideTeacherEmail(String outsideTeacherEmail) {
        this.outsideTeacherEmail = outsideTeacherEmail;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getTestDateTimeString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return sdf.format(this.testDateTime);
    }
}
