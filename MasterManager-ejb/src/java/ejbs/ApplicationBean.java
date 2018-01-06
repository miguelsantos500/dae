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
import exceptions.ApplicationStateException;
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
    @EJB
    private ProjectBean projectBean;
    @EJB
    private EmailBean emailBean;


    //recebe o username e o code do projectProposal e depois transforma em student e em projectProposal
    public Long create(String username, int code, String message) throws
            EntityDoesNotExistException, ApplicationNumberException, ApplicationStateException {
        Long newid = null;
        try {

            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistException("There is no student with that username.");
            }

            //se o estudante já se tiver candidatado a 5 propostas, não pode candidatar a mais nenhuma
            List<Application> studentApplications = student.getApplications();
            int applicationsNumber = studentApplications.size();

            if (applicationsNumber < 5) {
                ProjectProposal projectProposal = em.find(ProjectProposal.class, code);

                if (projectProposal == null) {
                    throw new EntityDoesNotExistException("There is no project proposal with that code.");
                }

                for (Application a : studentApplications) {

                    if (verifyApplicationStateAccepted(a.getId())) {
                        //mensagem a informar o estudante que já tem uma candidatura aceite
                        //todo - por isto no manager
                        // FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ja tem uma candidatura atribuida", 
                        //        "candidaturas"));
                        throw new ApplicationStateException("Já tem uma candidatura aceite");

                    }

                }

                Application application = new Application(student, projectProposal, message);

                student.addApplication(application);
                projectProposal.addApplication(application);

                em.persist(application);
                newid = application.getId();

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ja tem o num max de candidaturas", "candidaturas"));

            }
        } catch (ApplicationStateException | EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        return newid;
    }

    @PUT
    @Path("/addFileRecord/{applicationId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addFileRecord(@PathParam("applicationId") String idString, List<DocumentDTO> docs)
            throws EntityDoesNotExistException {
        try {

            Long id = Long.parseLong(idString);

            //procura a application criada 
            Application application = em.find(Application.class, id);

            if (application == null) {
                throw new EntityDoesNotExistException("Não existe nenhuma candidatura com esse id.");
            }

            EntitieGeneric<Application> generic = new EntitieGeneric<>(application);
            /*  Document document = new Document(doc.getFilepath(), doc.getDesiredName(),
                    doc.getMimeType(), generic);*/

            //////////////////////////////////////////////////EXPERIENCIA
            //cria a lista de documentos a persistir na bd
            List<Document> documents = new LinkedList<>();
            for (DocumentDTO d : docs) {
                documents.add(new Document(d.getFilepath(), d.getDesiredName(),
                        d.getMimeType(), generic));

            }

            //persiste cada documento na bd e adiciona à lista de documentos do Application
            for (Document d : documents) {
                em.persist(d);

            }
            application.setFileRecords(documents);
            //application.mappDocumentTypes();

            em.merge(application);
            ////////////////////////////////////////////////////////FIM EXPERIENCIA

            // em.persist(document);
            // application.setFileRecord(document);
        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
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

            if (verifyApplicationStateAccepted(application.getId())) {
                //nao pode actualizar uma candidatua que ja esta validada
                return;
            } else if (verifyApplicationStateNotAccepted(application.getId())) {
                //nao pode actualizar uma candidatura recusada
                return;
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
    
    public void update(ApplicationDTO applicationDTO, DocumentDTO[] documents) 
            throws MyConstraintViolationException, EntityDoesNotExistException {
        
        update(applicationDTO);
        
        try {
            Application application = em.find(Application.class, applicationDTO.getId());

            if (application == null) {
                throw new EntityDoesNotExistException(
                        "Não existe uma candidatura com o id: "
                        + applicationDTO.getId());
            }
            
            List<Document> fileRecords = application.getFileRecords();
            
            List<DocumentDTO> updatedDocuments = new LinkedList<>();
            
            for(int i = 0; i< documents.length; i++) {
                DocumentDTO documentDTO = documents[i];
                DocumentDTO updatedDocument;
                Document fileRecord = fileRecords.get(i);
                if(documentDTO == null){
                    updatedDocument = new DocumentDTO(fileRecord.getFilepath(),
                            fileRecord.getDesiredName(), fileRecord.getMimeType());
                }else{
                    updatedDocument = documentDTO;
                }
                
                updatedDocuments.add(updatedDocument);
                
            }
            addFileRecord(String.valueOf(application.getId()), updatedDocuments);
            
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

            rejectApplications(projectProposal.getCode());

            projectBean.create(projectProposal, application.getStudent(), 
                    projectProposal.getProponent().getUsername());
            
            /*emailBean.send(application.getStudent().getEmail(), "Candidatura", 
                    "A sua candidatura para o projeto " + projectProposal.getTitle() +
                    " foi aceite.\n\nBom Trabalho!");
            emailBean.send(projectProposal.getProponent().getEmail(), "Proposta de Trabalho", 
                    "O estudante " + application.getStudent().getEmail() + " foi aceite para "
                            + "realizar a sua proposta de trabalho.");*/
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public void rejectApplications(int projectCode) {
        try {

            List<Application> applications
                    = em.createNamedQuery("getAllProjectProposalApplicants", Application.class).
                            setParameter("code", projectCode).getResultList();

            for (Application application : applications) {
                if (application.getApplicationState().equals(ApplicationState.PENDING)) {
                    application.setApplicationState(ApplicationState.NOT_ACCEPTED);
                    em.merge(application);
                }
            }

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/allApplicants/{projectCode}")
    public List<ApplicationDTO> getAllProjectProposalApplicants(@PathParam("projectCode") String projectCode) {

        try {
            List<Application> applications
                    = em.createNamedQuery("getAllProjectProposalApplicants", Application.class).
                            setParameter("code", Integer.parseInt(projectCode)).getResultList();
            return applicationsToDTOs(applications);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    //recebe o id da candidatura e o index do documento a remover

    public void removeFileRecord(Long id, int index) throws EntityDoesNotExistException {
        try {
            Application application = em.find(Application.class, id);
            if (application == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhuma candidatura com esse id.");
            }

            //vai buscar o documento pelo id
            // Document document = application.getFileByIndex(index);
            //vai buscar o documento pelo id
            Document document = em.find(Document.class, application.getFileByIndex(index).getId());

            //codigo anterior
            // Document document = em.find(Document.class, application.getFileRecord().getId());
            if (document == null) {
                throw new EntityDoesNotExistException(
                        "Não existe nenhum documento com esse código.");
            }

            application.setFileRecords(null);
            em.merge(application);
            em.remove(document);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    //tem que passar a receber o id do documento 
    public DocumentDTO getDocument(Long id) throws EntityDoesNotExistException {
        System.out.println("Id: " + id);
        Application application = em.find(Application.class, id);

        if (application == null) {
            throw new EntityDoesNotExistException();
        }

        //ESTA HARDCODED PARA PODER TESTAS
        return new DocumentDTO(application.getFileRecords().get(0).getId(), application.getFileRecords().get(0).getFilepath(),
                application.getFileRecords().get(0).getDesiredName(), application.getFileRecords().get(0).getMimeType());

        //codigoanterior
        /*   return new DocumentDTO(application.getFileRecord().getId(), application.getFileRecord().getFilepath(),
                application.getFileRecord().getDesiredName(), application.getFileRecord().getMimeType());*/
    }

    public boolean verifyApplicationStateAccepted(Long id) {

        boolean stateAccepted = false;

        Application application = em.find(Application.class, id);
        if (application.getApplicationState() == ApplicationState.ACCEPTED) {
            stateAccepted = true;
        }

        return stateAccepted;
    }

    public boolean verifyApplicationStateNotAccepted(Long id) {

        boolean stateNotAccepted = false;

        Application application = em.find(Application.class, id);

        if (application.getApplicationState() == ApplicationState.NOT_ACCEPTED) {
            stateNotAccepted = true;
        }

        return stateNotAccepted;
    }

    public boolean verifyApplicationStatePending(Long id) {

        boolean statePending = false;

        Application application = em.find(Application.class, id);

        if (application.getApplicationState() == ApplicationState.PENDING) {
            statePending = true;
        }

        return statePending;
    }

    /* (Long id, int projectProposalCode, Student student, ProjectProposal projectProposal, String applyingMessage,
            ApplicationState applicationState, String documentName) */
    public ApplicationDTO applicationToDTO(Application application) {

        List<Document> fileRecords = application.getFileRecords();
        int size = fileRecords.size();

        String cv = "";
        String presentationLetter = "";
        String certificate = "";
        
        String cvPath = "";
        String presentationLetterPath = "";
        String certificatePath = "";
        
        if (size > 0) {
            cv = fileRecords.get(0).getDesiredName();
            cvPath = fileRecords.get(0).getFilepath();
        }
        if (size > 1) {
            presentationLetter = fileRecords.get(1).getDesiredName();
            presentationLetterPath = fileRecords.get(1).getFilepath();
        }
        if (size > 2) {
            certificate = fileRecords.get(2).getDesiredName();
            certificatePath = fileRecords.get(2).getFilepath();
        }

        return new ApplicationDTO(
                application.getId(),
                application.getProjectProposal().getCode(),
                application.getStudent(),
                application.getProjectProposal(),
                application.getApplyingMessage(),
                application.getApplicationState(),
                /* application.getFileRecord().getDesiredName(),*/
                cv,
                certificate,
                presentationLetter,
                cvPath,
                presentationLetterPath,
                certificatePath,
                application.getStudent().getName());

    }

    List<ApplicationDTO> applicationsToDTOs(List<Application> applications) {
        List<ApplicationDTO> dtos = new ArrayList<>();

        for (Application a : applications) {
            dtos.add(applicationToDTO(a));
        }

        return dtos;
    }

}
