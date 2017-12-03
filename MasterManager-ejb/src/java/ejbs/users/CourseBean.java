
package ejbs.users;

import dtos.CourseDTO;
import entities.Course;
import exceptions.EntityAlreadyExistsException;
import exceptions.MyConstraintViolationException;
import exceptions.Utils;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;

/**
 *
 * @author Soraia <soraiabasso@outlook.pt>
 */
@Stateless
public class CourseBean {
@PersistenceContext
    private EntityManager em;

    public void create(int code, String name)
            throws EntityAlreadyExistsException, MyConstraintViolationException {
        try {
            if (em.find(Course.class, code) != null) {
                throw new EntityAlreadyExistsException("A course with that code already exists.");
            }
            em.persist(new Course(code, name));

        } catch (EntityAlreadyExistsException e) {
            throw e;
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(Utils.getConstraintViolationMessages(e));
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    public List<CourseDTO> getAll() {
        try {
            List<Course> courses = (List<Course>) em.createNamedQuery("getAllCourses").getResultList();
            return coursesToDTOs(courses);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    List<CourseDTO> coursesToDTOs(List<Course> courses) {
        List<CourseDTO> dtos = new ArrayList<>();
        for (Course c : courses) {
            dtos.add(new CourseDTO(c.getCourseCode(), c.getCourseName()));            
        }
        return dtos;
    }

}
