package web;

import dtos.CourseDTO;
import dtos.ProjectProposalDTO;
import dtos.PublicTestDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import dtos.UserDTO;
import ejbs.PublicTestBean;
import ejbs.users.CCPUserBean;
import ejbs.users.InstitutionBean;
import ejbs.users.StudentBean;
import ejbs.users.TeacherBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

@ManagedBean
@SessionScoped
public class AdministratorManager {

    /**
     * ** Beans ***
     */
    @EJB
    private CCPUserBean ccpUserBean;
    @EJB
    private InstitutionBean institutionBean;
    @EJB
    private StudentBean studentBean;
    @EJB
    private TeacherBean teacherBean;

    /**
     * ** newObjects ***
     */
    @EJB
    private PublicTestBean publicTestBean;

    private StudentDTO newStudent;
    private TeacherDTO newTeacher;
    private CourseDTO newCourse;

    /**
     * ** currentObjects ***
     */
    private StudentDTO currentStudent;
    private TeacherDTO currentTeacher;
    private CourseDTO currentCourse;

    /**
     * ** Other ***
     */
    private PublicTestDTO newPublicTest;
    private PublicTestDTO currentPublicTest;
    private ProjectProposalDTO currentProjectProposal;

    private UIComponent component;
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String dateNow = dtf.format(LocalDate.now());

    private Client client;
    private final String baseUri
            = "http://localhost:8080/MasterManager-war/webapi";

    public AdministratorManager() {
        newStudent = new StudentDTO();
        newCourse = new CourseDTO();
        newPublicTest = new PublicTestDTO();
        client = ClientBuilder.newClient();
    }

    public String createStudent() {

        try {
            studentBean.create(
                    newStudent.getUsername(),
                    newStudent.getPassword(),
                    newStudent.getName(),
                    newStudent.getEmail(),
                    newStudent.getCourseCode());
            newStudent.reset();

        } catch (Exception e) {
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
                    .get(new GenericType<List<StudentDTO>>() {
                    });

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllStudents AdministratorManager",
                    logger);

        }
        for (UserDTO s : returnedStudents) {
            logger.warning(s.getUsername());
        }
        return returnedStudents;
    }

    ///////////////////////////////////////////TEACHERS//////////////////////////////////////////
    public String createTeacher() {

        try {
            teacherBean.create(
                    newTeacher.getUsername(),
                    newTeacher.getPassword(),
                    newTeacher.getName(),
                    newTeacher.getEmail());
            newTeacher.reset();

        } catch (Exception e) {
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
                    .get(new GenericType<List<TeacherDTO>>() {
                    });

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllTeachers AdministratorManager",
                    logger);

        }
        return returnedTeachers;
    }
    
    public List<TeacherDTO> getAllTeachersOrder() {
        List<TeacherDTO> returnedTeachers = null;
        try {
            returnedTeachers = client.target(baseUri)
                    .path("/teachers/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TeacherDTO>>() {
                    });
            List<TeacherDTO> aux = new LinkedList<>(returnedTeachers);
            for (TeacherDTO returnedTeacher : returnedTeachers) {
                System.out.println("SOU CCPUSER ? [" + returnedTeacher.getName() + "]");
                if(ccpUserBean.isCCPUser(returnedTeacher.getEmail())) {
                    aux.remove(returnedTeacher);
                    aux.add(0,returnedTeacher);
                    System.out.println("SIM [" + returnedTeacher.getName() + "]");
                } 
            }
            
            returnedTeachers = aux;
            
        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllTeachers AdministratorManager",
                    logger);

        }
        return returnedTeachers;
    }
    
    

    public List<ProjectProposalDTO> getAllProjectProposals() {
        List<ProjectProposalDTO> returnedProjectProposals = null;
        try {
            returnedProjectProposals = client.target(baseUri)
                    .path("/projectProposals/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ProjectProposalDTO>>() {
                    });

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado", component, logger);

        }
        return returnedProjectProposals;
    }

    public ProjectProposalDTO getCurrentProjectProposal() {
        return currentProjectProposal;
    }

    public void setCurrentProjectProposal(ProjectProposalDTO currentProjectProposal) {
        this.currentProjectProposal = currentProjectProposal;
    }

    ////////////// PUBLIC TEST ///////////////////
    public String createPublicTest() {

        try {    
            publicTestBean.create(
                    newPublicTest.getCode(),
                    newPublicTest.getTitle(),
                    newPublicTest.getTestDateTime(),
                    newPublicTest.getPlace(),
                    newPublicTest.getLink(),
                    newPublicTest.getJuryPresidentUsername(),
                    newPublicTest.getAdvisorUsername(),
                    newPublicTest.getOutsideTeacherName(),
                    newPublicTest.getOutsideTeacherEmail(),
                    newPublicTest.getStudentUsername());
            newPublicTest.reset();

        } catch (EntityAlreadyExistsException | EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, "Erro ao criar a prova pública!",
                    component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<PublicTestDTO> getAllPublicTests() {
        try {
            return publicTestBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!",
                    logger);
            return null;
        }
    }

    public String updatePublicTest() {
        try {
            publicTestBean.update(
                    currentPublicTest.getCode(),
                    currentPublicTest.getTitle(),
                    currentPublicTest.getTestDateTime(),
                    currentPublicTest.getPlace(),
                    currentPublicTest.getLink(),
                    currentPublicTest.getJuryPresidentUsername(),
                    currentPublicTest.getOutsideTeacherName(),
                    currentPublicTest.getOutsideTeacherEmail());

        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public void removePublicTest(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("publicTestCode");
            int code = Integer.parseInt(param.getValue().toString());
            publicTestBean.remove(code);
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }
    }

    public PublicTestDTO getNewPublicTest() {
        return newPublicTest;
    }

    public void setNewPublicTest(PublicTestDTO newPublicTest) {
        this.newPublicTest = newPublicTest;
    }

    public String getDateNow() {
        return dateNow;
    }

    public void setDateNow(String dateNow) {
        this.dateNow = dateNow;
    }

    public PublicTestDTO getCurrentPublicTest() {
        return currentPublicTest;
    }

    public void setCurrentPublicTest(PublicTestDTO currentPublicTest) {
        this.currentPublicTest = currentPublicTest;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

}
