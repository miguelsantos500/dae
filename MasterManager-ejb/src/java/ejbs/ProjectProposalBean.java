/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ProjectProposalDTO;
import entities.project.ProjectProposal;
import entities.project.ProjectProposalState;
import entities.project.ProjectType;
import entities.users.Institution;
import entities.users.Proponent;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.ProjectProposalNotPendingException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
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
            List<ProjectProposal> projectProposals = (List<ProjectProposal>) em.createNamedQuery("getAllProjectProposals").getResultList();
            return projectProposalsToDTOs(projectProposals);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("allFinished")
    public List<ProjectProposalDTO> getAllFinished() {
        try {
            List<ProjectProposal> projectProposals = 
                    em.createNamedQuery("getAllProjectProposalsFinished", ProjectProposal.class).
                            setParameter("state", ProjectProposalState.FINISHED).getResultList();
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

            //TODO
            //: DO
            //projectProposal.getProponent().removeProjectProposal(projectProposal);
            em.remove(projectProposal);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(ProjectProposalDTO projectProposalDTO)
            throws EntityDoesNotExistException, MyConstraintViolationException,
            MyConstraintViolationException {
        try {
            ProjectProposal projectProposal = em.find(ProjectProposal.class, projectProposalDTO.getCode());
            if (projectProposal == null) {
                throw new EntityDoesNotExistException(
                        "Não existe uma Proposta de Trabalho com o código: "
                        + projectProposalDTO.getCode());
            }

            Proponent proponent = em.find(Proponent.class, projectProposalDTO.getProponentUsername());
            if (proponent == null) {
                throw new EntityDoesNotExistException(
                        "Não existe um Proponente com o Username:"
                        + projectProposalDTO.getProponentUsername());
            }

            //todo: actualizar bibliography e outras listas???, successRequirements, supports, scientificAreas
            ProjectType projectType = ProjectType.valueOf(projectProposalDTO.getProjectTypeString());
            projectProposal.setProjectType(projectType);
            projectProposal.setTitle(projectProposalDTO.getTitle());
            projectProposal.setProponent(proponent);
            projectProposal.setProjectAbstract(projectProposalDTO.getProjectAbstract());
            projectProposal.setWorkPlan(projectProposalDTO.getWorkPlan());
            projectProposal.setWorkPlace(projectProposalDTO.getWorkPlace());
            projectProposal.setBudget(projectProposalDTO.getBudget());

            em.merge(projectProposal);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(
                    Utils.getConstraintViolationMessages(e));
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
                    projectProposal.getSupports(),
                    projectProposal.getProjectProposalState()));
        }
        return dtos;

    }

    public List<ProjectProposalDTO> search(String searchProjectProposal) {
        try {

            List<ProjectProposal> projects = em.createNamedQuery("getAllProjectProposals").getResultList();
            List<ProjectProposal> matchedProjectProposals = new LinkedList<>();

            for (ProjectProposal p : projects) {
                if ((p.getTitle().toLowerCase()).contains(searchProjectProposal.toLowerCase())) {
                    matchedProjectProposals.add(p);
                } else if ((Integer.toString(p.getCode())).contains(searchProjectProposal)) {
                    matchedProjectProposals.add(p);
                }
            }

            return projectProposalsToDTOs(matchedProjectProposals);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public void approveProjectProposal(int code, boolean approved) {
        try {
            ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
            if (projectProposal == null) {
                throw new EntityDoesNotExistException(
                        "There is no Project Proposal with code: "
                        + code);
            }
            if(projectProposal.getProjectProposalState() != ProjectProposalState.PENDING) {
                throw new ProjectProposalNotPendingException(
                        "This Project Proposal is not in state PENDING, is in state: "
                        + projectProposal.getProjectProposalState());
            }
            if(approved) {
                projectProposal.setProjectProposalState(ProjectProposalState.ACCEPTED);
            }else {
                projectProposal.setProjectProposalState(ProjectProposalState.NOT_ACCEPTED);                
            }
            
            
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }
}
