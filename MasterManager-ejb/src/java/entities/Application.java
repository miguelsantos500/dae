
package entities;

import entities.project.ProjectProposal;
import entities.users.Student;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
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
            query = "SELECT a FROM Application a"),})

public class Application implements Serializable {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
   @ManyToOne
   @JoinColumn(name="SUDENT_USERNAME")
   private Student student;
   
   @ManyToOne
   @JoinColumn(name="PROJECT_PROPOSAL_CODE")
   private ProjectProposal projectProposal; 
   
   @NotNull
   private String applyingMessage;
   
   @OneToOne
   @JoinColumn(name = "FILE_ID")
   private Document fileRecord;
   
   

    public Application() {
    }

    public Application(Student student, ProjectProposal projectProposal, String applyingMessage) {
        
        this.student = student;
        this.projectProposal = projectProposal;
        this.applyingMessage = applyingMessage;
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
    
     public Document getFileRecord() {
        if (this.fileRecord == null) {
            return new Document();
        }
        return fileRecord;
    }

    public void setFileRecord(Document fileRecord) {
        this.fileRecord = fileRecord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
   
   
   
}
