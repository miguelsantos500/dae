package ejbs.users;

import dtos.StudentDTO;
import entities.Course;
import entities.project.ProjectProposal;
import entities.users.Student;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.StudentAlreadyAppliedException;
import exceptions.StudentNotAppliedException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
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

    public List<StudentDTO> search(String searchName) {
        try {
            /**
             * ********debug*
             */
            System.out.println("DEBUG - entrou no StudentBean.search()");
            List<Student> students = em.createNamedQuery("getAllStudents").getResultList();
            List<Student> matchedStudents = new LinkedList<>();

            for (Student s : students) {
                if ((s.getUsername().toLowerCase()).contains(searchName.toLowerCase())) {
                    matchedStudents.add(s);
                } else if ((s.getName().toLowerCase()).contains(searchName.toLowerCase())) {
                    matchedStudents.add(s);
                } else if ((s.getEmail().toLowerCase()).contains(searchName.toLowerCase())) {
                    matchedStudents.add(s);
                }
            }
            /**
             * ********debug*
             */
            System.out.println("DEBUG - entrou no StudentBean.search()");
            return studentsToDTOs(matchedStudents);

        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

 /*   public void assigneStudent(String username, int code) throws EntityDoesNotExistException,
            StudentAlreadyAppliedException {
        try {

            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistException("There is no student with that username.");
            }

            ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
            if (projectProposal == null) {
                throw new EntityDoesNotExistException("There is no project proposal with that code.");
            }

            if (student.getProjectProposals().contains(projectProposal)) {
                throw new StudentAlreadyAppliedException("That student is already applied to that proposal.");
            }

            if (projectProposal.getStudents().contains(student)) {
                throw new StudentAlreadyAppliedException("That student is already applied to that proposal.");
            }

            student.addProjectProposal(projectProposal);
            projectProposal.addStudent(student);

        } catch (EntityDoesNotExistException | StudentAlreadyAppliedException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }*/

 /*   public void unussigneStudent(String username, int code) throws EntityDoesNotExistException,
            StudentNotAppliedException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistException("There is no such student.");
            }

            ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
            if (projectProposal == null) {
                throw new EntityDoesNotExistException("There is no such project proposal.");
            }

            if (!projectProposal.getStudents().contains(student)) {
                throw new StudentNotAppliedException();
            }

            student.removeProjectProposal(projectProposal);
            projectProposal.removeStudent(student);

        } catch (EntityDoesNotExistException | StudentNotAppliedException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }*/

    public Collection<StudentDTO> getAssignedStudents(int code) throws EntityDoesNotExistException{
        
        try {
        ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
        if(projectProposal == null){
            throw new EntityDoesNotExistException("There is no project proposal with that code.");
        }
        
        return new LinkedList<>();//studentsToDTOs(projectProposal.getStudents());
            
        } catch (EntityDoesNotExistException e) {
            throw e;
        }catch (Exception e){
            throw new EJBException(e.getMessage());
        }
        
    
    }

 /*   public Collection<StudentDTO> getUnappliedStudents(int code)throws EntityDoesNotExistException {
        try {
            ProjectProposal projectProposal = em.find(ProjectProposal.class, code);
            if(projectProposal == null){
                throw new EntityDoesNotExistException();
            }
            List<Student> studentsApplied = em.createNamedQuery("getAllProjectProposalStudents")
                    .setParameter("code", projectProposal.getCode())
                    .getResultList();
            
            studentsApplied.removeAll(studentsApplied);
            
            return studentsToDTOs(studentsApplied);
            
        }catch(EntityDoesNotExistException e){
            throw e;
        }
        catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }*/
    
    public StudentDTO getStudent(String username){
        
        StudentDTO student = em.find(StudentDTO.class, username);
        
        return student;
    }

}
