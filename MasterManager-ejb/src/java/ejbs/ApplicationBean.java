/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ApplicationDTO;
import entities.Application;
import entities.project.ProjectProposal;
import entities.users.Student;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Path;

/**
 *
 * @author Soraia <soraiabasso@outlook.pt>
 */
@Stateless
@Path("/applications")
public class ApplicationBean {

    @PersistenceContext
    private EntityManager em;

    //recebe o username e o code do projectProposal e depois transforma em student e em projectProposal
    public void create(String username, int code, String message) throws
            EntityDoesNotExistException {

        try {
            Student student = em.find(Student.class, username);
            if ( student == null) {
                throw new EntityDoesNotExistException("There is no student with that username.");
            }
            
            ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
            if ( projectProposal == null) {
                throw new EntityDoesNotExistException("There is no project proposal with that code.");
            }
            
            Application application = new Application(student, projectProposal, message);
            
            student.addApplication(application);
            em.persist(application);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

     
    }

    public ApplicationDTO applicationToDTO(Application application) {
        return new ApplicationDTO(
                application.getStudent(),
                application.getProjectProposal(),
                application.getApplyingMessage());
    }
    
    List<ApplicationDTO> ApplicationsToDTOs(List<Application> applications) {
        List<ApplicationDTO> dtos = new ArrayList<>();

        for (Application a : applications) {
            dtos.add(applicationToDTO(a));
        }

        return dtos;
    }

}
