package entities;

import entities.project.ProjectProposal;
import entities.users.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import static javax.persistence.EnumType.STRING;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "APPLICATIONS")
@NamedQueries({
    @NamedQuery(name = "getAllApplications",
            query = "SELECT a FROM Application a")
    ,
    @NamedQuery(name = "getAllProjectProposalApplicants",
            query = "SELECT a FROM Application a "
            + "WHERE a.projectProposal.code = :code")})
public class Application implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "SUDENT_USERNAME")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "PROJECT_PROPOSAL_CODE")
    private ProjectProposal projectProposal;

    @NotNull
    private String applyingMessage;

    /*  @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "FILE_ID") 
    private Document fileRecord;  */
    @OneToMany(mappedBy = "application", cascade = CascadeType.REMOVE)
    private List<Document> fileRecords;

    @NotNull
    @Enumerated(STRING)
    private ApplicationState applicationState;

    public Application() {
        this.fileRecords = new ArrayList<>(3);
    }

    public Application(Student student, ProjectProposal projectProposal, String applyingMessage) {

        this.student = student;
        this.projectProposal = projectProposal;
        this.applyingMessage = applyingMessage;
        this.applicationState = ApplicationState.PENDING;
        this.fileRecords = new ArrayList<>(3);

    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ProjectProposal getProjectProposal() {
        return projectProposal;
    }

    public void setProjectProposal(ProjectProposal projectProposal) {
        this.projectProposal = projectProposal;
    }

    public String getApplyingMessage() {
        return applyingMessage;
    }

    public void setApplyingMessage(String applyingMessage) {
        this.applyingMessage = applyingMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ApplicationState getApplicationState() {
        return applicationState;
    }

    public void setApplicationState(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

 
    public List<Document> getFileRecords() {
        return fileRecords;
    }

    public void setFileRecords(List<Document> fileRecords) {
        this.fileRecords = fileRecords;
    }
    
    public Document getFileByIndex(int index){
        return fileRecords.get(index);
    }

}
