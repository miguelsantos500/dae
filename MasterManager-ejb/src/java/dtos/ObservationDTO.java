
package dtos;

import entities.project.ProjectProposal;
import entities.project.ProjectProposalState;
import java.io.Serializable;

public class ObservationDTO implements Serializable{

    protected String message;
    protected ProjectProposalState projectProposalState;
    protected ProjectProposal projectProposal;

    public ObservationDTO() {
    }

    public ObservationDTO(String message, ProjectProposalState projectProposalState, ProjectProposal projectProposal) {
        this.message = message;
        this.projectProposalState = projectProposalState;
        this.projectProposal = projectProposal;
    }

    
    
    public void reset(){
        setMessage(null);
        setProjectProposalState(null);
        setProjectProposal(null);
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

    public ProjectProposal getProjectProposal() {
        return projectProposal;
    }

    public void setProjectProposal(ProjectProposal projectProposal) {
        this.projectProposal = projectProposal;
    }
    
    
}
