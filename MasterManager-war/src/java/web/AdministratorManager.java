
package web;

import dtos.CourseDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import dtos.UserDTO;
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
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@ManagedBean
@SessionScoped
public class AdministratorManager {

    /**** Beans ****/
    @EJB
    private CCPUserBean ccpUserBean;
    @EJB
    private InstitutionBean institutionBean;
    @EJB
    private StudentBean studentBean;
    @EJB
    private TeacherBean teacherBean;

     /**** newObjects ****/
    private StudentDTO newStudent;
    private TeacherDTO newTeacher;
    private CourseDTO newCourse;
    
     /**** currentObjects ****/
    private StudentDTO currentStudent;
    private TeacherDTO currentTeacher;
    private CourseDTO currentCourse;
    
     /**** Other ****/
    private UIComponent component;
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");
    private Client client;
    private final String baseUri
            = "http://localhost:8080/MasterManager-war/webapi";
       
    public AdministratorManager(){
        newStudent = new StudentDTO();
        newCourse = new CourseDTO();
        client = ClientBuilder.newClient();
    }
    ///////////////////////////////////////////STUDENTS//////////////////////////////////////////
    public String createStudent(){
        
        try{
          studentBean.create(
                  newStudent.getUsername(),
                  newStudent.getPassword(),
                  newStudent.getName(),
                  newStudent.getEmail(),
                  newStudent.getCourseCode());
          newStudent.reset();
        
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Erro inesperado no createStudent do AdministratorManager", component, logger);
            e.printStackTrace();
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
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllStudents AdministratorManager", 
                     logger);
           
        }
        return returnedStudents;
    }
    
    
        ///////////////////////////////////////////TEACHERS//////////////////////////////////////////
    public String createTeacher(){
        
        try{
          teacherBean.create(
                  newTeacher.getUsername(),
                  newTeacher.getPassword(),
                  newTeacher.getName(),
                  newTeacher.getEmail());
          newTeacher.reset();
        
        }catch(Exception e){
            FacesExceptionHandler.handleException(e, "Erro inesperado no createTeacher do AdministratorManager", component, logger);
            e.printStackTrace();
            return null;
        }
        return "index?faces-redirect=true";
    }
    
    public List<TeacherDTO> getAllTeachers() {
        List<TeacherDTO> returnedTeachers = null;
        try {
            returnedTeachers = client.target(baseUri)
                    .path("/teachers/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TeacherDTO>>(){
                    });

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllTeachers AdministratorManager", 
                     logger);
           
        }
        return returnedTeachers;
    }

}
