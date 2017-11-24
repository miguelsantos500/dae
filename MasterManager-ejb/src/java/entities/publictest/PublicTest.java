package entities.publictest;

import entities.users.CCPUser;
import entities.users.Student;
import entities.users.Teacher;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;



@Entity
@Table(name = "PUBLIC_TESTS")
@NamedQuery(name = "getAllPublicTests",
        query = "SELECT s FROM PublicTest s ORDER BY s.testDate, s.testHour")
public class PublicTest implements Serializable {

    @Id
    private int code;

    @NotNull
    private String title;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "TEST_DATE")
    private Date testDate;

    @NotNull
    @Column(name = "TEST_HOUR")
    private String testHour;

    @NotNull
    private String place;

    @NotNull
    private String link;
    
    @NotNull
    //@OneToMany
    private CCPUser ccpUserJury;

    //@OneToMany
    private List<Student> students;
    
    //@ManyToMany
    private List<Teacher> teachersJurys;
    
    private File fileRecord;

    public PublicTest() {
        teachersJurys = new LinkedList<>();
        students = new LinkedList<>();
    }

    public PublicTest(int code, String title, Date testDate,
            String testHour, String place, String link, CCPUser ccpUserJury) {
        this.code = code;
        this.title = title;
        this.testDate = testDate;
        this.testHour = testHour;
        this.place = place;
        this.link = link;
        this.ccpUserJury = ccpUserJury;
        teachersJurys = new LinkedList<>();
        students = new LinkedList<>();
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

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getTestHour() {
        return testHour;
    }

    public void setTestHour(String testHour) {
        this.testHour = testHour;
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

    public List<Student> getStudent() {
        return students;
    }

    public void setStudent(List<Student> student) {
        this.students = student;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public CCPUser getCcpUserJury() {
        return ccpUserJury;
    }

    public void setCcpUserJury(CCPUser ccpUserJury) {
        this.ccpUserJury = ccpUserJury;
    }

    public List<Teacher> getTeachersJurys() {
        return teachersJurys;
    }

    public void setTeachersJurys(List<Teacher> teachersJurys) {
        this.teachersJurys = teachersJurys;
    }

    public File getFileRecord() {
        return fileRecord;
    }

    public void setFileRecord(File fileRecord) {
        this.fileRecord = fileRecord;
    }
    
    

}
