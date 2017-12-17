package entities.users;

import entities.Course;
import entities.publictest.PublicTest;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table( name = "STUDENTS")
@NamedQueries({
    @NamedQuery(name = "getAllStudents",
    query = "SELECT s FROM Student s ORDER BY s.name")})
public class Student extends User implements Serializable {

    @ManyToOne
    @JoinColumn(name="COURSE_CODE")
    @NotNull (message="A student must have a course")
    private Course course;
    
    @OneToOne
    @JoinColumn(name = "PUBLIC_CODE")
    private PublicTest publicTest;
    
    public Student() {
    }

    public Student(String username, String password, String name, String email, Course course) {
        super(username, password, name, email);
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public PublicTest getPublicTest() {
        return publicTest;
    }

    public void setPublicTest(PublicTest publicTest) {
        this.publicTest = publicTest;
    }

    
}
