/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.project.Project;
import entities.project.ProjectProposal;
import entities.users.Student;
import entities.users.Teacher;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
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
    
    public void create(ProjectProposal projectProposal, Student student, 
            String proponentUsername )
            throws EntityAlreadyExistsException,
            EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Project project = new Project(projectProposal, student);
            em.persist(project);
            
            Teacher teacher = em.find(Teacher.class, proponentUsername);
            if (teacher != null){
                project.addTeacher(teacher);
                teacher.setProject(project);
                em.merge(teacher);
            }
            student.setProject(project);
            em.merge(student);
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            e.getConstraintViolations().forEach((obj) -> System.out.print(obj));
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }

    }
}
