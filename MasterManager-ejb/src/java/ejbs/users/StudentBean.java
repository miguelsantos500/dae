package ejbs.users;

import entities.Course;
import users.Student;
import users.User;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;


@Stateless
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

    public void update(String username, String password, String name, String email)
            throws EntityDoesNotExistException, MyConstraintViolationException,
            MyConstraintViolationException {
        try {
            Student student = em.find(Student.class, username);
            if (student == null) {
                throw new EntityDoesNotExistException(
                        "Não existe um utilizador Estudante com esse username.");
            }
            student.setName(name);
            student.setEmail(email);

            em.merge(student);

        } catch (EntityDoesNotExistException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(
                    Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
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
    

}
