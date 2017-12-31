/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.project;

import entities.users.CCPUser;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "OBSERVATIONS")
@NamedQueries({
    @NamedQuery(name = "getAllObservations",
            query = "SELECT o FROM Observation o"),
    @NamedQuery(name = "getAllProjectProposalObservations",
            query = "SELECT o FROM Observation o "
            + "WHERE o.projectProposal.code = :code")})
public class Observation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String message;
    
    @NotNull
    private ProjectProposalState projectProposalState; 
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "PROJECT_PROPOSAL_CODE")
    private ProjectProposal projectProposal;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "CCPUSER_CODE")
    private CCPUser cCPUser;
    

    public Observation() {
    }

    public Observation(String message, ProjectProposalState projectProposalState, 
            ProjectProposal projectProposal, CCPUser cCPUser) {
        this.message = message;
        this.projectProposalState = projectProposalState;
        this.projectProposal = projectProposal;
        this.cCPUser = cCPUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public CCPUser getcCPUser() {
        return cCPUser;
    }

    public void setcCPUser(CCPUser cCPUser) {
        this.cCPUser = cCPUser;
    }

    
    
    
}
