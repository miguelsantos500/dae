package entities.users;

import entities.Application;
import entities.Course;
import entities.project.ProjectProposal;
import entities.publictest.PublicTest;
import entities.users.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "STUDENTS")
@NamedQueries({
    @NamedQuery(name = "getAllStudents",
            query = "SELECT s FROM Student s ORDER BY s.name"),
})
public class Student extends User implements Serializable {

    @ManyToOne
    @JoinColumn(name = "COURSE_CODE")
    @NotNull(message = "A student must have a course")
    private Course course;

    @OneToOne(cascade=CascadeType.REMOVE)
    @JoinColumn(name = "PUBLIC_CODE")
    private PublicTest publicTest;

    @ManyToOne(cascade=CascadeType.REMOVE)
    @JoinColumn(name = "PROJECT_PROPOSAL_CODE")
    private ProjectProposal projectProposal;
    
    @OneToMany(mappedBy="student", cascade=CascadeType.REMOVE)
    private List<Application> applications;

    public Student() {
    }

    public Student(String username, String password, String name, String email, Course course) {
        super(username, password, GROUP.Student, name, email);
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

    public ProjectProposal getProjectProposal() {
        return projectProposal;
    }

    public void setProjectProposal(ProjectProposal projectProposal) {
        this.projectProposal = projectProposal;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

   
    
    public void addApplication(Application app){
        applications.add(app);
    }
    
    public void removeApplication(Application app) {
        applications.remove(app);
    }


}
