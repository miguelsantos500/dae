package entities.project;

import entities.Document;
import entities.project.ProjectProposal;
import entities.users.Student;
import entities.users.Teacher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PROJECTS")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @OneToMany(mappedBy = "project")
    private Teacher[] teachers;

    @NotNull
    private String messageToTeacher;

    @NotNull
    @OneToOne
    @JoinColumn(name = "PROJECT_PROPOSAL_CODE")
    private ProjectProposal projectProposal;

    //TODO por isto a funcionar!
    private Document articles;
    private Document sourceCode;
    private Document assignment;
    private Document other;
    
     @NotNull
    private String documentsDescription;

    @NotNull
    @OneToOne(mappedBy = "project")
    private Student student;

    public Project() {
    }

    public Project(Teacher[] teachers, String messageToTeacher, 
                    ProjectProposal projectProposal, Student student, String docDescription) {
       
        this.teachers = teachers;
        this.messageToTeacher = messageToTeacher;
        this.projectProposal = projectProposal;
        this.student = student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher[] getTeachers() {
        return teachers;
    }

    public void setTeachers(Teacher[] teachers) {
        this.teachers = teachers;
    }

    public String getMessageToTeacher() {
        return messageToTeacher;
    }

    public void setMessageToTeacher(String messageToTeacher) {
        this.messageToTeacher = messageToTeacher;
    }

    public ProjectProposal getProjectProposal() {
        return projectProposal;
    }

    public void setProjectProposal(ProjectProposal projectProposal) {
        this.projectProposal = projectProposal;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
