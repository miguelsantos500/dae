/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ProjectDTO;
import entities.project.Project;
import entities.project.ProjectProposal;
import entities.users.Student;
import entities.users.Teacher;
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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Soraia <soraiabasso@outlook.pt>
 */
@Stateless
@Path("/project")
public class ProjectBean {

    @PersistenceContext
    private EntityManager em;

    public void create(ProjectProposal projectProposal, Student student,
            String proponentUsername)
            throws EntityAlreadyExistsException,
            EntityDoesNotExistException, MyConstraintViolationException {
        try {
            Project project = new Project(projectProposal, student);
            em.persist(project);

            Teacher teacher = em.find(Teacher.class, proponentUsername);
            if (teacher != null) {
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

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ProjectDTO> getAll() {
        try {
            List<Project> projects = (List<Project>) em.createNamedQuery("getAllProjects").getResultList();
            return projectsToDTOs(projects);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    private List<ProjectDTO> projectsToDTOs(List<Project> projects) {
        List<ProjectDTO> dtos = new ArrayList<>();
        for (Project s : projects) {
            dtos.add(projectToDTO(s));
        }
        return dtos;
    }

    private ProjectDTO projectToDTO(Project projects) {
        ProjectDTO projectDTO = new ProjectDTO(
                projects.getId(),
                projects.getMessageToTeacher(),
                projects.getProjectProposal().getTitle(),
                projects.getProjectProposal().getCode(),
                projects.getStudent().getName(),
                projects.getStudent().getUsername(),
                projects.getTeachersNames());
        
        for (Teacher teacher : projects.getTeachers()) {
            projectDTO.addToListTeacherName(teacher.getName());
            projectDTO.addToListTeacherUsername(teacher.getUsername());
        }
        
        return projectDTO;
    
    }

    public void enrollTeacher(List<String> teachers, Long id)
            throws EntityDoesNotExistException {
        try {
            Project project = em.find(Project.class, id);
            if (project == null) {
                throw new EntityDoesNotExistException("There is no Project with that id.");
            }

            Teacher teacher;
            for (String teacherUsername : teachers) {
                teacher = em.find(Teacher.class, teacherUsername);
                if (teacher == null) {
                    throw new EntityDoesNotExistException("There is no Teacher with that username.");
                }
                project.addTeacher(teacher);
            }

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
