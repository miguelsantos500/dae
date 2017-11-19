package entities.users;

import entities.Course;
import entities.users.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="STUDENTS")
public class Student extends User implements Serializable {

    @ManyToOne
    @JoinColumn(name="COURSE_CODE")
    @NotNull (message="A student must have a course")
    private Course course;
    
    public Student() {
    }

    public Student(String username, String password, String name, String email, Course course) {
        super(username, password, name, email);
    }

}
