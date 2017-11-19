
package web;

import dtos.CourseDTO;
import dtos.StudentDTO;
import ejbs.users.CCPUserBean;
import ejbs.users.InstitutionBean;
import ejbs.users.StudentBean;
import ejbs.users.TeacherBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@ManagedBean
@SessionScoped
public class AdminstratorManager {
    
    @EJB
    private CCPUserBean ccpUserBean;
     @EJB
    private InstitutionBean institutionBean;
      @EJB
    private StudentBean studentBean;
       @EJB
    private TeacherBean teacherBean;
       
    private StudentDTO newStudent;
    private CourseDTO newCourse;
    private UIComponent component;
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    
    private Client client;
    private final String baseUri
            = "http://localhost:8080/MasterManager-war/webapi";
       
    public AdminstratorManager(){
        newStudent = new StudentDTO();
        newCourse = new CourseDTO();
    }
    
    public String creaTeStudent(){
        
        try{
          studentBean.create(
                  newStudent.getUsername(),
                  newStudent.getPassword(),
                  newStudent.getName(),
                  newStudent.getEmail(),
                  newStudent.getCourseCode());
          newStudent.reset();
        
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Erro inesperado", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }
    
    public List<StudentDTO> getAllStudents() {
        List<StudentDTO> returnedStudents = null;
        try {
            returnedStudents = client.target(baseUri)
                    .path("/students/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<StudentDTO>>(){
                    });

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado", logger);
           
        }
        return returnedStudents;
    }
    
}
