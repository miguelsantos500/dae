/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "PROJECT_PROPOSALS")
@NamedQuery(name = "getAllProjectProposals",
        query = "SELECT p FROM ProjectProposal p") /*TODO: ORDER BY*/
public class ProjectProposal implements Serializable {

    @Id
    private int code;

    
    
    
    
    
    @Override
    public String toString() {
        return "entities.ProjectProposal[ id=" + code + " ]";
    }
    
}
