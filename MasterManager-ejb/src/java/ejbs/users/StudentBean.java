package ejbs.users;

import dtos.StudentDTO;
import entities.Course;
import entities.users.Student;
import entities.users.User;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ws.rs.Path;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("/students")
public class StudentBean {

    @PersistenceContext
    private EntityManager em;

   
    public void create(String username, String password, String name, String email, int courseCode)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Student.class, username) != null) {
                throw new EntityAlreadyExistsException("A user with that username already exists.");
            }
            Course course = em.find(Course.class, courseCode);
            if (course == null) {
                throw new EJBException("There is no course with that code.");
            }
            Student student = new Student(username, password, name, email, course);
            course.addStudent(student);
            em.persist(student);
        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateStudent(StudentDTO studentDTO)
            throws MyConstraintViolationException {
        try {
           
            Course course = em.find(Course.class, studentDTO.getCourseCode());
            if (course == null) {
             //   throw new EJBException(e.getMessage());
            }
            Student student = em.find(Student.class, studentDTO.getUsername());
            if (student == null) {
              //  throw new EntityDoesNotExistsException("There is no student with that username.");
            }
 
            student.setPassword(studentDTO.getPassword());
            student.setName(studentDTO.getName());
            student.setEmail(studentDTO.getEmail());
           // student.getCourse().removeStudent(student);
            student.setCourse(course);
            course.addStudent(student);
            em.merge(student);
 
        } catch (Exception e) {
            throw e;
        }
    }

    public void remove(String username) throws EntityDoesNotExistException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistException(
                        "Não existe um utilizador Estudante com esse username.");
            }

            em.remove(student);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<StudentDTO> getAll() {
        try {
            List<Student> students = em.createNamedQuery("getAllStudents").getResultList();
            return studentsToDTOs(students);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    StudentDTO studentToDTO(Student student) {
        return new StudentDTO(
                student.getUsername(),
                null,
                student.getName(),
                student.getEmail(),
                student.getCourse().getCourseCode(),
                student.getCourse().getCourseName());
    }

    List<StudentDTO> studentsToDTOs(List<Student> students) {
        List<StudentDTO> dtos = new ArrayList<>();

        for (Student s : students) {
            dtos.add(studentToDTO(s));
        }

        return dtos;
    }
    

}
