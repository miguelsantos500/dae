/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.project.Observation;
import entities.project.ProjectProposal;
import entities.project.ProjectProposalState;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Path;
import exceptions.Utils;

/**
 *
 * @author Miguel
 */
@Stateless
@Path("/observations")
public class ObservationBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ProjectProposalBean projectProposalBean;
    
    public void create(String message,
            String projectProposalStateString, int projectProposalCode)
            throws EntityAlreadyExistsException,
            EntityDoesNotExistException, MyConstraintViolationException {
        try {

            ProjectProposal projectProposal = em.find(ProjectProposal.class,
                    projectProposalCode);
            if (projectProposal == null) {
                throw new EntityDoesNotExistException(
                        "There is no Project Proposal with that code.("
                        + projectProposalCode + ")");
            }

            ProjectProposalState state = Enum.valueOf(ProjectProposalState.class, 
                    projectProposalStateString);
            
            Observation observation = new Observation(message, state, projectProposal);
            
            em.persist(observation);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            e.getConstraintViolations().forEach((obj) -> System.out.print(obj));
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

    }

}
