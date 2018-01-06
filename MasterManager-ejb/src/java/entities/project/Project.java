package entities.project;

import entities.Document;
import entities.users.Student;
import entities.users.Teacher;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PROJECTS")
@NamedQuery(name = "getAllProjects",
        query = "SELECT s FROM Project s ORDER BY s.id")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @OneToMany(mappedBy = "project")
    private List<Teacher> teachers;

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

    private String documentsDescription;

    @NotNull
    @OneToOne(mappedBy = "project")
    private Student student;

    public Project() {
        this.teachers = new LinkedList<>();
    }

    public Project(ProjectProposal projectProposal, Student student) {
        this.projectProposal = projectProposal;
        this.student = student;
        this.teachers = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
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
    
    public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
    }
    
    public void removeTeacher(Teacher teacher) {
        this.teachers.remove(teacher);
    }

    public String getTeachersNames() {
        if (teachers.isEmpty()) {
            return "Não Atribuido";
        }
        
        StringBuilder sb = new StringBuilder();
        for (Teacher teacher : teachers) {
            sb.append(teacher.getName());
            sb.append(", ");
        }
        
        sb.setLength(sb.length() - 2);
        return sb.toString();
    }
}
