
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import entities.users.Student;

@Entity
@Table(name = "COURSES",
uniqueConstraints =
@UniqueConstraint(columnNames = {"NAME"}))
@NamedQueries({
    @NamedQuery(name = "getAllCourses",
    query = "SELECT c FROM Course c ORDER BY c.name"),
    @NamedQuery(name = "getAllCoursesNames",
    query = "SELECT c.name FROM Course c ORDER BY c.name")})
public class Course implements Serializable {

    @Id
    private int courseCode;
    @NotNull (message="O curso tem que ter um nome.")
    private String name;
    @OneToMany(mappedBy="course", cascade = CascadeType.REMOVE)
    private List<Student> students;

    public Course() {
        this.students = new LinkedList<>();
    }

    public Course(int courseCode, String name) {
        this.courseCode = courseCode;
        this.name = name;
        this.students = new LinkedList<>();
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    

}
