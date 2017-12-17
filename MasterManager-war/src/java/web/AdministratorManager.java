package web;

import dtos.CourseDTO;
import dtos.InstitutionDTO;
import dtos.ProjectProposalDTO;
import dtos.PublicTestDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import ejbs.PublicTestBean;
import ejbs.users.CCPUserBean;
import ejbs.users.CourseBean;
import ejbs.users.InstitutionBean;
import ejbs.users.StudentBean;
import ejbs.users.TeacherBean;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javax.ws.rs.client.Entity;
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
    @EJB
    private CourseBean courseBean;
    @EJB
    private PublicTestBean publicTestBean;

    /**
     * ** newObjects ***
     */
    private StudentDTO newStudent;
    private TeacherDTO newTeacher;
    private CourseDTO newCourse;
    private InstitutionDTO newInstitution;
    private PublicTestDTO newPublicTest;

    private String searchableName;

    /**
     * ** currentObjects ***
     */
    private StudentDTO currentStudent;
    private TeacherDTO currentTeacher;
    private CourseDTO currentCourse;
    private InstitutionDTO currentInstitution;
    private PublicTestDTO currentPublicTest;

    /**
     * ** Other ***
     */
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
        newTeacher = new TeacherDTO();
        newCourse = new CourseDTO();
        newPublicTest = new PublicTestDTO();
        newInstitution = new InstitutionDTO();
        client = ClientBuilder.newClient();
    }

    ///////////////////////////////////////////STUDENTS//////////////////////////////////////////
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
        return returnedStudents;
    }

    public String updateStudent() {
        try {

            client.target(baseUri)
                    .path("/students/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentStudent));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }

        return "index?faces-redirect=true";
    }

    public void removeStudent(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("studentUsername");
            String id = param.getValue().toString();
            studentBean.remove(id);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        }
    }

    public StudentDTO getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(StudentDTO currentStudent) {
        this.currentStudent = currentStudent;
    }
/*
    public List<StudentDTO> getSearchStudent() {

        List<StudentDTO> returnedStudents = null;
        List<StudentDTO> foundStudents = null;
        try {
            returnedStudents = client.target(baseUri)
                    .path("/students/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<StudentDTO>>() {
                    });

            for (StudentDTO s : returnedStudents) {
                if (searchableName.trim().equals(s.getName())) {
                    foundStudents.add(s);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getSearchStudents AdministratorManager",
                    logger);

        }
        return foundStudents;
    }

    public String getSearchableName() {
        return searchableName;
    }

    public void setSearchableName(String searchableName) {
        this.searchableName = searchableName;
    }
*/
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

    public TeacherDTO getCurrentTeacher() {
        return currentTeacher;
    }

    public void removeTeacher(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("teacherUsername");
            String id = param.getValue().toString();
            teacherBean.remove(id);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        }
    }

    public String updateTeacher() {
        try {

            client.target(baseUri)
                    .path("/teachers/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentTeacher));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }

        return "index?faces-redirect=true";
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

    ///////////////////////////////////////////INSTITUTIONS//////////////////////////////////////////
    public String createInstitution() {

        try {
            institutionBean.create(
                    newInstitution.getUsername(),
                    newInstitution.getPassword(),
                    newInstitution.getName(),
                    newInstitution.getEmail());
            newInstitution.reset();

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado no createTeacher do AdministratorManager", component, logger);
            e.printStackTrace();
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<InstitutionDTO> getAllInstitutions() {
        List<InstitutionDTO> returnedInstitutions = null;
        try {
            returnedInstitutions = client.target(baseUri)
                    .path("/institutions/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<InstitutionDTO>>() {
                    });

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllTeachers AdministratorManager",
                    logger);

        }
        return returnedInstitutions;
    }

    public InstitutionDTO getNewInstitution() {
        return newInstitution;
    }

    public void setNewInstitution(InstitutionDTO newInstitution) {
        this.newInstitution = newInstitution;
    }

    public InstitutionDTO getCurrentInstitution() {
        return currentInstitution;
    }

    public void setCurrentInstitution(InstitutionDTO currentInstitution) {
        this.currentInstitution = currentInstitution;
    }

    public void removeInstitution(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("institutionUsername");
            String id = param.getValue().toString();
            institutionBean.remove(id);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        }
    }

    public String updateInstitution() {
        try {

            client.target(baseUri)
                    .path("/institutions/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentInstitution));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }

        return "index?faces-redirect=true";
    }

    ///////////////////////////////////////////COURSES//////////////////////////////////////////
    public String createCourse() {
        try {
            courseBean.create(
                    newCourse.getCode(),
                    newCourse.getName());
            newCourse.reset();
        } catch (MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return null;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<CourseDTO> getAllCourses() {
        try {
            return courseBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
            return null;
        }
    }

    ///////////////////////////////////////////Getters e setters tem que ser organizado//////////////////////////////////////////
    public CourseDTO getCurrentCourse() {
        return currentCourse;
    }

    public void setCurrentTeacher(TeacherDTO currentTeacher) {
        this.currentTeacher = currentTeacher;
    }

    public void setCurrentCourse(CourseDTO currentCourse) {
        this.currentCourse = currentCourse;
    }

    public StudentDTO getNewStudent() {
        return newStudent;
    }

    public void setNewStudent(StudentDTO newStudent) {
        this.newStudent = newStudent;
    }

    public TeacherDTO getNewTeacher() {
        return newTeacher;
    }

    public void setNewTeacher(TeacherDTO newTeacher) {
        this.newTeacher = newTeacher;
    }

    public CourseDTO getNewCourse() {
        return newCourse;
    }

    public void setNewCourse(CourseDTO newCourse) {
        this.newCourse = newCourse;
    }

    public InstitutionBean getInstitutionBean() {
        return institutionBean;
    }

    public void setInstitutionBean(InstitutionBean institutionBean) {
        this.institutionBean = institutionBean;
    }

    public StudentBean getStudentBean() {
        return studentBean;
    }

    public void setStudentBean(StudentBean studentBean) {
        this.studentBean = studentBean;
    }

    public TeacherBean getTeacherBean() {
        return teacherBean;
    }

    public void setTeacherBean(TeacherBean teacherBean) {
        this.teacherBean = teacherBean;
    }

    public CourseBean getCourseBean() {
        return courseBean;
    }

    public void setCourseBean(CourseBean courseBean) {
        this.courseBean = courseBean;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

}
