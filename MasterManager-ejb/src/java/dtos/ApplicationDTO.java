
package dtos;

import entities.project.ProjectProposal;
import entities.users.Student;
import java.io.Serializable;


public class ApplicationDTO implements Serializable  {
  
   private Student student;
   private ProjectProposal projectProposal;
   private String applyingMessage;

    public ApplicationDTO() {
    }

   
    public ApplicationDTO(Student student, ProjectProposal projectProposal, String applyingMessage) {
        this.student = student;
        this.projectProposal = projectProposal;
        this.applyingMessage = applyingMessage;
    }
   
   public void reset(){
        student = null;
        projectProposal = null;
        applyingMessage = null;
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
   
   
}
