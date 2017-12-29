/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ApplicationDTO;
import dtos.DocumentDTO;
import entities.Application;
import entities.ApplicationState;
import entities.Document;
import entities.EntitieGeneric;
import entities.project.ProjectProposal;
import entities.project.ProjectProposalState;
import entities.users.Student;
import exceptions.ApplicationNotPendingException;
import exceptions.ApplicationNumberException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Soraia <soraiabasso@outlook.pt>
 */
@Stateless
@Path("/applications")
public class ApplicationBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private ProjectProposalBean projectProposalBean;

    //recebe o username e o code do projectProposal e depois transforma em student e em projectProposal
    public Long create(String username, int code, String message) throws
            EntityDoesNotExistException, ApplicationNumberException {
        Long newid = null;
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistException("There is no student with that username.");
            }

            //se o estudante já se tiver candidatado a 5 propostas, não pode candidatar a mais nenhuma
            int applicationsNumber = student.getApplications().size();

            if (applicationsNumber < 5) {
                ProjectProposal projectProposal = em.find(ProjectProposal.class, code);

                if (projectProposal == null) {
                    throw new EntityDoesNotExistException("There is no project proposal with that code.");
                }

                Application application = new Application(student, projectProposal, message);

                student.addApplication(application);
                projectProposal.addApplication(application);

                em.persist(application);
                newid = application.getId();

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ja tem o num max de candidaturas", "candidaturas"));

            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return newid;
    }

    @PUT
    @Path("/addFileRecord/{applcationId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addFileRecord(@PathParam("applcationId") String idString, DocumentDTO doc)
            throws EntityDoesNotExistException {
        try {

            Long id = Long.parseLong(idString);

            //procura a application criada 
            Application application = em.find(Application.class, id);

            if (application == null) {
                throw new EntityDoesNotExistException("Não existe nenhuma candidatura com esse id.");
            }

            EntitieGeneric<Application> generic = new EntitieGeneric<>(application);
            Document document = new Document(doc.getFilepath(), doc.getDesiredName(),
                    doc.getMimeType(), generic);

            /*  DocumentApplication document = new DocumentApplication(doc.getFilepath(), doc.getDesiredName(),
                    doc.getMimeType(), application);*/
            em.persist(document);
            application.setFileRecord(document);
            //  em.merge(application);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    /* (Long id, int projectProposalCode, Student student, ProjectProposal projectProposal, String applyingMessage,
            ApplicationState applicationState, String documentName) */
    public ApplicationDTO applicationToDTO(Application application) {
        return new ApplicationDTO(
                application.getId(),
                application.getProjectProposal().getCode(),
                application.getStudent(),
                application.getProjectProposal(),
                application.getApplyingMessage(),
                application.getApplicationState(),
                application.getFileRecord().getDesiredName(),
                application.getStudent().getName());

    }

    List<ApplicationDTO> applicationsToDTOs(List<Application> applications) {
        List<ApplicationDTO> dtos = new ArrayList<>();

        for (Application a : applications) {
            dtos.add(applicationToDTO(a));
        }

        return dtos;
    }

    public List<ApplicationDTO> getStudentApplications(String username) 
            throws EntityDoesNotExistException, ApplicationNumberException {

        try {
            Student student = em.find(Student.class, username);

            if (student == null) {
                throw new EntityDoesNotExistException();
            }
            List<Application> applications;

            applications = student.getApplications();

            if (applications.isEmpty()) {

                //todo - mensagem a dizer que não há candidaturas para aquele estudante
                throw new ApplicationNumberException("Não tem candidaturas.");
            }

            return applicationsToDTOs(applications);

        } catch (EntityDoesNotExistException | ApplicationNumberException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void update(ApplicationDTO applicationDTO) throws MyConstraintViolationException, EntityDoesNotExistException {

        try {
            Application application = em.find(Application.class, applicationDTO.getId());

            if (application == null) {
                throw new EntityDoesNotExistException(
                        "Não existe uma candidatura com o id: "
                        + applicationDTO.getId());
            }

            application.setApplyingMessage(applicationDTO.getApplyingMessage());

            em.merge(application);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(
                    Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void remove(String id) throws EntityDoesNotExistException {
        try {
            Application application = em.find(Application.class, Long.parseLong(id));
            if (application == null) {
                throw new EntityDoesNotExistException("Não existe uma candidatura com esse id.");
            }

            //removo a candidatura  da lista de candidaturas da projectProposal
            application.getProjectProposal().removeApplication(application);

            //removo a candidatura da lista de candidaturas do estudante
            application.getStudent().removeApplication(application);

            //apago a candiatura da tabela
            em.remove(application);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<ApplicationDTO> search(String searchableApplication, String username) {
        try {
            //vou buscar o estudante para que possa procurar apenas candidaturas dele
            Student student = em.find(Student.class, username);

            List<Application> applications = student.getApplications();
            List<Application> matchedApplications = new LinkedList<>();

            for (Application a : applications) {
                //procura por id da candidatura
                if ((Long.toString(a.getId())).contains(searchableApplication)) {
                    matchedApplications.add(a);

                    //procura por codigo da projectProposal associada à candidatura
                } else if ((Integer.toString(a.getProjectProposal().getCode())).contains(searchableApplication)) {
                    matchedApplications.add(a);

                    //procura pelo titulo da proposta de projecto associada à candidatura
                } else if ((a.getProjectProposal().getTitle().toUpperCase()).contains(searchableApplication.toUpperCase())) {
                    matchedApplications.add(a);
                    //procura pelo tipo de project proposal associado à candidatura
                } else if ((a.getProjectProposal().getProjectType().toString().toUpperCase()).contains(searchableApplication.toUpperCase())) {
                    matchedApplications.add(a);
                }

            }
            return applicationsToDTOs(matchedApplications);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

    }

    public void approveApplication(Long id) {
        try {
            Application application = em.find(Application.class, id);
            if (application == null) {
                throw new EntityDoesNotExistException(
                        "There is no Application with id: "
                        + id);
            }

            ProjectProposal projectProposal = em.find(ProjectProposal.class,
                    application.getProjectProposal().getCode());
            if (projectProposal == null) {
                throw new EntityDoesNotExistException(
                        "There is no Project Propososal with");
            }

            if (application.getApplicationState() != ApplicationState.PENDING) {
                throw new ApplicationNotPendingException(
                        "This Application is not in state PENDING, is in state: "
                        + application.getApplicationState());
            }

            application.setApplicationState(ApplicationState.ACCEPTED);
            em.merge(application);
            projectProposal.setProjectProposalState(ProjectProposalState.ASSIGNED);
            em.merge(projectProposal);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/allApplicants/{projectCode}")
    public List<ApplicationDTO> getAllFinished(@PathParam("projectCode") String projectCode) {
        try {
            List<Application> applications
                    = em.createNamedQuery("getAllProjectProposalsFinished", Application.class).
                            setParameter("code", Integer.parseInt(projectCode)).getResultList();
            return applicationsToDTOs(applications);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void removeFileRecord(Long id) throws EntityDoesNotExistException {
        try {
            Application application = em.find(Application.class, id);
            if (application == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhuma candidatura com esse id.");
            }

            Document document = em.find(Document.class, application.getFileRecord().getId());
            if (document == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhum documento com esse código.");
            }

            em.remove(document);
            application.setFileRecord(new Document());

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public DocumentDTO getDocument(Long id) throws EntityDoesNotExistException {
        System.out.println("Id: " + id);
        Application application = em.find(Application.class, id);

        if (application == null) {
            throw new EntityDoesNotExistException();
        }

        return new DocumentDTO(application.getFileRecord().getId(), application.getFileRecord().getFilepath(),
                application.getFileRecord().getDesiredName(), application.getFileRecord().getMimeType());
    }

}
