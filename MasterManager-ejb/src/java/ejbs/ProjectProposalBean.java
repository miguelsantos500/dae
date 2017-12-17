/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ProjectProposalDTO;
import entities.project.ProjectProposal;
import entities.project.ProjectType;
import entities.users.Proponent;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Miguel
 */
@Stateless
@Path("/projectProposals")
public class ProjectProposalBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int code, String projectTypeString,
            String title, List<String> scientificAreas, String proponentUsername,
            String projectAbstract, List<String> objectives,
            List<String> bibliography, String workPlan,
            String workPlace, List<String> successRequirements,
            String budget, List<String> supports)
            throws EntityAlreadyExistsException,
            EntityDoesNotExistException, MyConstraintViolationException {

        try {
            if (em.find(ProjectProposal.class, code) != null) {
                throw new EntityAlreadyExistsException("A Project Proposal with that code already exists");
            }
            //TODO: Mudar para uma nova Interface
            Proponent proponentUser = em.find(Proponent.class, proponentUsername);
            if (proponentUser == null) {
                throw new EntityDoesNotExistException("There is no Proponent with that username.");
            }

            ProjectType projectType = Enum.valueOf(ProjectType.class, projectTypeString);

            ProjectProposal projectProposal = new ProjectProposal(
                    code, projectType, title, scientificAreas, proponentUser,
                    projectAbstract, objectives, bibliography, workPlan, workPlace,
                    successRequirements, budget, supports);
            em.persist(projectProposal);
            //TODO
            //proponentUser.addProjectProposal(projectProposal);
        } catch (EntityAlreadyExistsException | EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            e.getConstraintViolations().forEach((obj) -> System.out.print(obj));
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ProjectProposalDTO> getAll() {
        try {
            List<ProjectProposal> projectProposals = (List<ProjectProposal>) 
                    em.createNamedQuery("getAllProjectProposals").getResultList();
            return projectProposalsToDTOs(projectProposals);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public ProjectProposal getProjectProposal(int code) {
        try {
            ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
            return projectProposal;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(int code) throws EntityDoesNotExistException {
        try {
            ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
            if (projectProposal == null) {
                throw new EntityDoesNotExistException("There is no Project Proposal with that code.");
            }

            //TODO: DO
            //projectProposal.getProponent().removeProjectProposal(projectProposal);
            em.remove(projectProposal);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    List<ProjectProposalDTO> projectProposalsToDTOs(
            List<ProjectProposal> projectProposals) {

        List<ProjectProposalDTO> dtos = new ArrayList<>();
        for (ProjectProposal projectProposal : projectProposals) {
            dtos.add(new ProjectProposalDTO(projectProposal.getCode(),
                    projectProposal.getProjectType().name(),
                    projectProposal.getTitle(),
                    projectProposal.getProponent().getUsername(),
                    projectProposal.getProjectAbstract(),
                    projectProposal.getWorkPlan(),
                    projectProposal.getWorkPlace(),
                    projectProposal.getBudget(),
                    projectProposal.getScientificAreas(),
                    projectProposal.getObjectives(),
                    projectProposal.getBibliography(),
                    projectProposal.getSuccessRequirements(),
                    projectProposal.getSupports()));
        }
        return dtos;
        
    }
}
