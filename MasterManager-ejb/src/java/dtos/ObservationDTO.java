
package dtos;

import entities.project.ProjectProposalState;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Observation")
@XmlAccessorType(XmlAccessType.FIELD)
public class ObservationDTO implements Serializable{

    private String message;
    private ProjectProposalState projectProposalState;
    private int projectProposalCode;
    private String cCPUserUsername;
    
    

    public ObservationDTO() {
    }

    public ObservationDTO(String message, ProjectProposalState projectProposalState,
            int projectProposalCode, String cCPUserUsername) {
        this.message = message;
        this.projectProposalState = projectProposalState;
        this.projectProposalCode = projectProposalCode;
        this.cCPUserUsername = cCPUserUsername;
    }

    
    
    public void reset(){
        setMessage(null);
        setProjectProposalState(null);
        setProjectProposalCode(-1);
        setcCPUserUsername(null);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProjectProposalState getProjectProposalState() {
        return projectProposalState;
    }

    public void setProjectProposalState(ProjectProposalState projectProposalState) {
        this.projectProposalState = projectProposalState;
    }

    public int getProjectProposalCode() {
        return projectProposalCode;
    }

    public void setProjectProposalCode(int projectProposalCode) {
        this.projectProposalCode = projectProposalCode;
    }

    
    public String getcCPUserUsername() {
        return cCPUserUsername;
    }

    public void setcCPUserUsername(String cCPUserUsername) {
        this.cCPUserUsername = cCPUserUsername;
    }

    
    
    
    
}
