package web;

import dtos.CourseDTO;
import dtos.DocumentDTO;
import dtos.InstitutionDTO;
import dtos.ProjectProposalDTO;
import dtos.ProponentDTO;
import dtos.PublicTestDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import ejbs.ProjectProposalBean;
import ejbs.PublicTestBean;
import ejbs.users.CCPUserBean;
import ejbs.users.CourseBean;
import ejbs.users.InstitutionBean;
import ejbs.users.StudentBean;
import ejbs.users.TeacherBean;
import entities.project.ProjectType;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import util.URILookup;

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
    @EJB
    private ProjectProposalBean projectProposalBean;

    /**
     * ** newObjects ***
     */
    private StudentDTO newStudent;
    private TeacherDTO newTeacher;
    private CourseDTO newCourse;
    private InstitutionDTO newInstitution;
    private PublicTestDTO newPublicTest;
    private ProjectProposalDTO newProjectProposal;

    /**
     * ** currentObjects ***
     */
    private StudentDTO currentStudent;
    private TeacherDTO currentTeacher;
    private CourseDTO currentCourse;
    private InstitutionDTO currentInstitution;
    private PublicTestDTO currentPublicTest;
    private ProjectProposalDTO currentProjectProposal;

    /**
     * ***Searchable objects******
     */
    private String searchablePublicTest;
    private String searchableStudent;
    private String searchableTeacher;
    private String searchableCourse;
    private String searchableInstitution;

    /**
     * ** Other ***
     */
    @ManagedProperty(value = "#{uploadManager}")
    private UploadManager uploadManager;

    private String scientificAreasString;

    private String search;

    private UIComponent component;
    private static final Logger logger = Logger.getLogger("web.AdministratorManager");

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String dateNow = dtf.format(LocalDate.now());

    private Client client;
    private final String baseUri
            = "http://localhost:8080/MasterManager-war/webapi";

    private HtmlPanelGrid mainGrid;

    public AdministratorManager() {
        newStudent = new StudentDTO();
        newTeacher = new TeacherDTO();
        newCourse = new CourseDTO();
        newPublicTest = new PublicTestDTO();
        newInstitution = new InstitutionDTO();
        newProjectProposal = new ProjectProposalDTO();
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

    public List<StudentDTO> getSearchStudent() {
        try {
            System.out.println("entrou no getSearchStudent()");
            List<StudentDTO> foundStudents = studentBean.search(searchableStudent);
            return foundStudents;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error on getSearchStudent()!", logger);
            return null;
        }
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

    public List<TeacherDTO> getAllTeachersCCPOnTop() {
        List<TeacherDTO> returnedTeachers = null;
        try {
            returnedTeachers = client.target(baseUri)
                    .path("/teachers/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<TeacherDTO>>() {
                    });
            List<TeacherDTO> aux = new LinkedList<>(returnedTeachers);
            for (TeacherDTO teacherDTO : aux) {
                if (ccpUserBean.isCCPUser(teacherDTO.getEmail())) {
                    returnedTeachers.remove(teacherDTO);
                    returnedTeachers.add(0, teacherDTO);
                }
            }

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

    ////////////// PROJECT PROPOSAL ///////////////////
    public String createProjectProposal() {

        try {

            logger.info(scientificAreasString);

            

            ArrayList<String> bibliography = new ArrayList<>();

            projectProposalBean.create(
                    newProjectProposal.getCode(), newProjectProposal.getProjectTypeString(),
                    newProjectProposal.getTitle(),
                    newProjectProposal.getScientificAreas(),
                    newProjectProposal.getProponentUsername(),
                    newProjectProposal.getProjectAbstract(),
                    newProjectProposal.getScientificAreas(),//objectives,
                    bibliography,//bibliography,
                    newProjectProposal.getWorkPlan(),
                    newProjectProposal.getWorkPlace(),
                    newProjectProposal.getScientificAreas(),//successRequirements,
                    newProjectProposal.getBudget(),
                    newProjectProposal.getScientificAreas());//supports);

        } catch (EntityAlreadyExistsException | EntityDoesNotExistException
                | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, "Error Creating Project Proposal!",
                    component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }


    public List<TeacherDTO> getSearchTeacher() {
        try {
            List<TeacherDTO> foundTeachers = teacherBean.search(searchableTeacher);
            return foundTeachers;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error on getSearchStudent()!", logger);
            return null;
        }
    }

    public String getSearchableTeacher() {
        return searchableTeacher;
    }

    public void setSearchableTeacher(String searchableTeacher) {
        this.searchableTeacher = searchableTeacher;
    }

    /////////////////////////////////////////////PROJECT PROPOSALS/////////////////////////////////
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

    public List<ProponentDTO> getAllProponents() {
        List<ProponentDTO> returnedProponents = null;
        try {
            returnedProponents = client.target(baseUri)
                    .path("/proponents/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ProponentDTO>>() {
                    });

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllProponents AdministratorManager",
                    logger);

        }
        return returnedProponents;
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

    public List<PublicTestDTO> getSearchPublicTest() {
        try {
            List<PublicTestDTO> searchResults = publicTestBean.search(searchablePublicTest);
            return searchResults;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error no getSearchPublicTest!", logger);
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
    
    public void uploadFileRecord(UIComponent component) {
        try {            
            DocumentDTO document = new DocumentDTO(uploadManager.getCompletePathFile(), 
                                                    uploadManager.getFilename(), 
                                                    uploadManager.getFile().getContentType());
             
            UIParameter param = (UIParameter) component.findComponent("publicTestCode2");
            String code = param.getValue().toString();
           
            
            client.target(URILookup.getBaseAPI())
                    .path("/publictest/addFileRecord")
                    .path(code)
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(document));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro ao fazer o upload do ficheiro!", logger);
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
            FacesExceptionHandler.handleException(e, "Erro inesperado no createInstitution do AdministratorManager", component, logger);
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
    
    public List<InstitutionDTO> getSearchInstitution() {
        try {
            List<InstitutionDTO> foundInstitutions = institutionBean.search(searchableInstitution);
            return foundInstitutions;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error on getSearchStudent()!", logger);
            return null;
        }
    }

    public String getSearchableInstitution() {
        return searchableInstitution;
    }

    public void setSearchableInstitution(String searchableInstitution) {
        this.searchableInstitution = searchableInstitution;
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
    public ProjectProposalDTO getNewProjectProposal() {
        return newProjectProposal;
    }

    public void setNewProjectProposal(ProjectProposalDTO newProjectProposal) {
        this.newProjectProposal = newProjectProposal;
    }

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

    public String getSearchablePublicTest() {
        return searchablePublicTest;
    }

    public void setSearchablePublicTest(String searchablePublicTest) {
        this.searchablePublicTest = searchablePublicTest;
    }

    public String getSearchableStudent() {
        return searchableStudent;
    }

    public void setSearchableStudent(String searchableStudent) {
        this.searchableStudent = searchableStudent;
    }

    public ProjectType[] getProjectTypes() {
        return ProjectType.values();
    }

    public String getScientificAreasString() {
        return scientificAreasString;
    }

    public void setScientificAreasString(String scientificAreasString) {
        this.scientificAreasString = scientificAreasString;
    }

    public ProjectProposalDTO getCurrentProjectProposal() {
        return currentProjectProposal;
    }

    public void setCurrentProjectProposal(ProjectProposalDTO currentProjectProposal) {
        this.currentProjectProposal = currentProjectProposal;
    }

    public UploadManager getUploadManager() {
        return uploadManager;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }

}
