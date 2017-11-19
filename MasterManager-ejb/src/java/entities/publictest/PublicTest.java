package entities.publictest;

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
import users.Student;

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
    private List<Student> students;
    
    private List<Jury> jury;
    
    

    public PublicTest() {
        jury = new LinkedList<>();
        students = new LinkedList<>();
    }

    public PublicTest(int code,String title, Date testDate,
            String testHour, String place, String link) {
        this.code = code;
        this.title = title;
        this.testDate = testDate;
        this.testHour = testHour;
        this.place = place;
        this.link = link;
        jury = new LinkedList<>();
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

    public List<Jury> getJury() {
        return jury;
    }

    public void setJury(List<Jury> jury) {
        this.jury = jury;
    }

    public List<Student> getStudent() {
        return students;
    }

    public void setStudent(List<Student> student) {
        this.students = student;
    }
    
    

}
