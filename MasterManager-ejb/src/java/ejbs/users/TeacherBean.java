package ejbs.users;

import dtos.TeacherDTO;
import entities.users.Teacher;
import entities.users.User;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/teachers")
public class TeacherBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String username, String password, String name, String email)
            throws EntityAlreadyExistsException {
        try {
            if (em.find(User.class, username) != null) {
                throw new EntityAlreadyExistsException(
                        "Um utilizador já existe com esse username.");
            }

            Teacher teacher = new Teacher(username, password, name, email);
            em.persist(teacher);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateTeacher(TeacherDTO teacherDTO)
            throws MyConstraintViolationException, EntityDoesNotExistException {

        try {
            Teacher teacher = em.find(Teacher.class, teacherDTO.getUsername());
            if (teacher == null) {
                throw new EntityDoesNotExistException("There is no teacher with that username.");
            }

            teacher.setPassword(teacherDTO.getPassword());
            teacher.setName(teacherDTO.getName());
            teacher.setEmail(teacherDTO.getEmail());
            em.merge(teacher);

        } catch (EntityDoesNotExistException e) {
            throw e;
        }

    }

    public void remove(String username) throws EntityDoesNotExistException {
        try {
            Teacher teacher = em.find(Teacher.class, username);
            if (teacher == null) {
                throw new EntityDoesNotExistException(
                        "Não existe um utilizador Professore com esse username.");
            }

            em.remove(teacher);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<TeacherDTO> getAll() {
        try {
            List<Teacher> teachers = em.createNamedQuery("getAllTeachers").getResultList();
            return teachersToDTOs(teachers);
        } catch (Exception e) {
            e.printStackTrace();
            throw new EJBException(e.getMessage());
        }
    }

    public TeacherDTO teacherToDTO(Teacher teacher) {
        return new TeacherDTO(
                teacher.getUsername(),
                null,
                teacher.getName(),
                teacher.getEmail());
    }

    public List<TeacherDTO> teachersToDTOs(List<Teacher> teachers) {
        List<TeacherDTO> teacherdtos = new ArrayList<>();

        for (Teacher t : teachers) {
            teacherdtos.add(teacherToDTO(t));
        }
        return teacherdtos;
    }

   
    public List<TeacherDTO> search(String searchableTeacher) {

        try {
            List<Teacher> teachers = em.createNamedQuery("getAllTeachers").getResultList();
            List<Teacher> matchedTeachers = new LinkedList<Teacher>();

            for (Teacher t : teachers) {
                if ((t.getUsername().toLowerCase()).contains(searchableTeacher.toLowerCase())) {
                    matchedTeachers.add(t);
                } else if ((t.getName().toLowerCase()).contains(searchableTeacher.toLowerCase())) {
                    matchedTeachers.add(t);
                } else if ((t.getEmail().toLowerCase()).contains(searchableTeacher.toLowerCase())) {
                    matchedTeachers.add(t);
                }
            }

            return teachersToDTOs(matchedTeachers);
            
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
