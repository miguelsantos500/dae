/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ApplicationDTO;
import dtos.DocumentDTO;
import dtos.ProjectProposalDTO;
import entities.Application;
import entities.Document;
import entities.project.ProjectProposal;
import entities.users.Student;
import exceptions.ApplicationNumberException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import static javax.ws.rs.HttpMethod.POST;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
    public void create(String username, int code, String message) throws
            EntityDoesNotExistException, ApplicationNumberException {

        try {
            Student student = em.find(Student.class, username);
            if ( student == null) {
                throw new EntityDoesNotExistException("There is no student with that username.");
            }
            
            //se o estudante já se tiver candidatado a 5 propostas, não pode candidatar a mais nenhuma
            int applicationsNumber = student.getApplications().size();
            
            if(applicationsNumber<5){
                ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
                
            if ( projectProposal == null) {
                throw new EntityDoesNotExistException("There is no project proposal with that code.");
            }
            
            Application application = new Application(student, projectProposal, message);
            
            student.addApplication(application);
            projectProposal.addApplication(application);
            
            em.persist(application);
            }else{
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ja tem o num max de candidaturas", "candidaturas"));
        return ;
                
            }
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
     
    }
    
    @PUT
    @Path("/addFileApplication/{applcationId}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void addFileRecord(@PathParam("applcationId") String idString, DocumentDTO document)
            throws EntityDoesNotExistException {
       
        try {
            Long id = Long.parseLong(idString);
            
            //procura a application criada 
            Application application = em.find(Application.class, id);
            
            if (application == null) {
                throw new EntityDoesNotExistException("Não existe nenhuma candidatura com esse id.");
            }
            
         //   Document doc = new Document(document.getFilepath(), document.getDesiredName(), document.getMimeType(), application);
            
        } catch (Exception e) {
        }
        
      /*  try {
          

            Document document = new Document(doc.getFilepath(), doc.getDesiredName(), doc.getMimeType(), publicTest);
            em.persist(document);
            publicTest.setFileRecord(document);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }*/
    }

    public ApplicationDTO applicationToDTO(Application application) {
        return new ApplicationDTO(
                application.getId(),
               application.getProjectProposal().getCode(),
                application.getStudent(),
                application.getProjectProposal(),
                application.getApplyingMessage());
    }
    
    List<ApplicationDTO> applicationsToDTOs(List<Application> applications) {
        List<ApplicationDTO> dtos = new ArrayList<>();

        for (Application a : applications) {
            dtos.add(applicationToDTO(a));
        }

        return dtos;
    }

    public List<ApplicationDTO> getStudentApplications(String username) throws EntityDoesNotExistException{
        
        try {
            Student student = em.find(Student.class, username);
            
            if(student==null){
                throw new EntityDoesNotExistException();
            }
           List<Application> applications = new LinkedList<>();
           
           applications = student.getApplications();
           
           if(applications.isEmpty()){
               
               //todo - mensagem a dizer que não há candidaturas para aquele estudante
               throw new ApplicationNumberException("Não tem candidaturas.");
               
           }
            
           return applicationsToDTOs(applications);
            
           
        } catch (EntityDoesNotExistException e) {
            throw e;
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
        
    }
    
    /*public void remove(String username) throws EntityDoesNotExistException {
        try {
            Institution institution = em.find(Institution.class, username);
            if (institution == null) {
                throw new EntityDoesNotExistException(
                        "Não existe um utilizador Instituição com esse username.");
            }

            em.remove(institution);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    } */

    public void remove(String id) throws EntityDoesNotExistException{
        try {
            Application application = em.find(Application.class, Long.parseLong(id));
            if(application == null){
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
        }catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
