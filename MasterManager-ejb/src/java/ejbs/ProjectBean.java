/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.project.Project;
import entities.project.ProjectProposal;
import entities.project.ProjectType;
import entities.users.Proponent;
import entities.users.Student;
import entities.users.Teacher;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Path;

/**
 *
 * @author Soraia <soraiabasso@outlook.pt>
 */
@Stateless
@Path("/ProjectBean")
public class ProjectBean {

    @PersistenceContext
    private EntityManager em;
    
    public void create(List <Teacher> teachers, String messageToTeacher, 
                    ProjectProposal projectProposal, Student student, String docDescription)
            throws EntityAlreadyExistsException,
            EntityDoesNotExistException, MyConstraintViolationException {

        try {
            /*
            if (em.find(Project.class, student.getUsername()) != null) {
                throw new EntityAlreadyExistsException("A Project with that student already exists");
            }
            
            List<Teacher> teachersAssigned = new LinkedList<>();
            
            if (teachers.isEmpty()) {
                throw new EntityDoesNotExistException("There are no Teachers with that username.");
            }
            
            for (Teacher teacher : teachers) {
                
                teachersAssigned.add();
                
            }
            
            
            
            //criar o novo projecto
            Project project = new Project(teachersAssigned, messageToTeacher,
                                    projectProposal, student, docDescription);
            
           
            em.persist(project);
        } catch (EntityAlreadyExistsException | EntityDoesNotExistException e) {
            throw e;
     */
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            e.getConstraintViolations().forEach((obj) -> System.out.print(obj));
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

    }
}
