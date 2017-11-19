/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ProjectProposalDTO;
import entities.project.Budget;
import entities.project.ProjectProposal;
import entities.project.ProjectType;
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
import javax.validation.ConstraintViolationException;
import users.User;

/**
 *
 * @author Miguel
 */
@Stateless
public class ProjectProposalBean {

    @PersistenceContext
    private EntityManager em;

    public void create(int code, String projectTypeString,
            String title, List<String> scientificAreas, String preponentUsername,
            String projectAbstract, List<String> objectives,
            ArrayList<String> bibliography, String workPlan,
            String workPlace, List<String> successRequirements,
            Budget budget, List<String> supports)
            throws EntityAlreadyExistsException,
            EntityDoesNotExistException, MyConstraintViolationException {

        try {
            if (em.find(ProjectProposal.class, code) != null) {
                throw new EntityAlreadyExistsException("A Project Proposal with that code already exists");
            }
            //TODO: Mudar para uma nova Interface
            User preponentUser = em.find(User.class, preponentUsername);
            if (preponentUser == null) {
                throw new EntityDoesNotExistException("There is no User with that username.");
            }

            ProjectType projectType = Enum.valueOf(ProjectType.class, projectTypeString);

            ProjectProposal projectProposal = new ProjectProposal(
                    code, projectType, title, scientificAreas, preponentUser,
                    projectAbstract, objectives, bibliography, workPlan, workPlace,
                    successRequirements, budget, supports);
            em.persist(projectProposal);

            //TODO
            //preponentUser.addProjectProposal(projectProposal);
        } catch (EntityAlreadyExistsException | EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

    }

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
            //projectProposal.getPreponent().removeProjectProposal(projectProposal);
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
                    projectProposal.getProjectType(),
                    projectProposal.getTitle(),
                    projectProposal.getPreponent(),
                    projectProposal.getProjectAbstract(),
                    projectProposal.getWorkPlan(),
                    projectProposal.getWorkPlace()));
        }
        return dtos;
    }
}
