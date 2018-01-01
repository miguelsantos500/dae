package dtos;

import entities.ApplicationState;
import entities.project.ProjectProposal;
import entities.users.Student;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name = "Application")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplicationDTO implements Serializable {

    private Long id;
    //para que não faça ciclos na geração do XML
    @XmlTransient
    private Student student;
    private ProjectProposal projectProposal;
    private String applyingMessage;
    //private String documentName;
    private int projectProposalCode;
    private ApplicationState applicationState;
    private String studentName;
    private String cvName;
    private String certificateName;
    private String presentationLetterName;
    
    private String cvPath;
    private String certificatePath;
    private String presentationLetterPath;
    

    public ApplicationDTO() {
    }

    public ApplicationDTO(Long id, int projectProposalCode, Student student, 
            ProjectProposal projectProposal, String applyingMessage,
            ApplicationState applicationState, 
           /* String documentName, */
            String cvName,
            String certificateName,
            String presentationLetterName,
            String cvPath,
            String certificatePath,
            String presentationLetterPath,
            String studentName) {
        this.id = id;
        this.student = student;
        this.projectProposal = projectProposal;
        this.applyingMessage = applyingMessage;
        this.projectProposalCode = projectProposal.getCode();
        this.applicationState = applicationState;
        /*this.documentName= documentName;*/
        this.cvName = cvName;
        this.certificateName = certificateName;
        this.presentationLetterName = presentationLetterName;
        
        this.cvPath = cvPath;
        this.certificatePath = certificatePath;
        this.presentationLetterPath = presentationLetterPath;
        
        this.studentName = studentName;
    }

    public void reset() {
        this.student=null;
        this.id= null;
        projectProposal = null;
        this.projectProposalCode=0;
        applyingMessage = null;
        this.applicationState=null;
       /* this.documentName = null;*/
       this.cvName = null;
        this.certificateName =null;
        this.presentationLetterName = null;
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

    public int getProjectProposalCode() {
        return projectProposalCode;
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

   /* public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }*/

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCvName() {
        return cvName;
    }

    public void setCvName(String cvName) {
        this.cvName = cvName;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getPresentationLetterName() {
        return presentationLetterName;
    }

    public void setPresentationLetterName(String presentationLetterName) {
        this.presentationLetterName = presentationLetterName;
    }

    public String getCvPath() {
        return cvPath;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public String getPresentationLetterPath() {
        return presentationLetterPath;
    }

    public void setPresentationLetterPath(String presentationLetterPath) {
        this.presentationLetterPath = presentationLetterPath;
    }
    
    
    
    
    

}
