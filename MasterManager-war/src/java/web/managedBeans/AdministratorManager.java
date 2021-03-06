package web.managedBeans;

import dtos.ApplicationDTO;
import dtos.CourseDTO;
import dtos.DocumentDTO;
import dtos.InstitutionDTO;
import dtos.ObservationDTO;
import dtos.ProjectDTO;
import dtos.ProjectProposalDTO;
import dtos.ProponentDTO;
import dtos.PublicTestDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import ejbs.ApplicationBean;
import ejbs.ObservationBean;
import ejbs.ProjectBean;
import ejbs.ProjectProposalBean;
import ejbs.PublicTestBean;
import ejbs.users.CCPUserBean;
import ejbs.users.CourseBean;
import ejbs.users.InstitutionBean;
import ejbs.users.StudentBean;
import ejbs.users.TeacherBean;
import entities.project.ProjectProposalState;
import entities.project.ProjectType;
import entities.users.UserGroup;
import exceptions.ApplicationNumberException;
import exceptions.ApplicationStateException;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import org.primefaces.model.UploadedFile;
import util.URILookup;
import web.FacesExceptionHandler;
import web.UploadManager;
import web.UserManager;

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
    @EJB
    private ApplicationBean applicationBean;
    @EJB
    private ObservationBean observationBean;
    @EJB
    private ProjectBean projectBean;
    
    /**
     * ** newObjects ***
     */
    private StudentDTO newStudent;
    private TeacherDTO newTeacher;
    private CourseDTO newCourse;
    private InstitutionDTO newInstitution;
    private PublicTestDTO newPublicTest;
    private ProjectProposalDTO newProjectProposal;
    private ApplicationDTO newApplication;
    private ObservationDTO newObservation;

    /**
     * ** currentObjects ***
     */
    private StudentDTO currentStudent;
    private TeacherDTO currentTeacher;
    private CourseDTO currentCourse;
    private InstitutionDTO currentInstitution;
    private PublicTestDTO currentPublicTest;
    private ProjectProposalDTO currentProjectProposal;
    private ApplicationDTO currentApplication;
    private ProjectDTO currentProject;

    public ApplicationBean getApplicationBean() {
        return applicationBean;
    }

    public void setApplicationBean(ApplicationBean applicationBean) {
        this.applicationBean = applicationBean;
    }

    /**
     * ***Searchable objects******
     */
    private String searchablePublicTest;
    private String searchableStudent;
    private String searchableTeacher;
    private String searchableInstitution;
    private String searchableProjectProposal;

    /**
     * ** Other ***
     */
    @ManagedProperty(value = "#{uploadManager}")
    private UploadManager uploadManager;

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    private String scientificAreasString;
    
    private List<String> orientadores;

    private UIComponent component;
    private static final Logger logger = Logger.getLogger("web.managedBeans.AdministratorManager");

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
        newApplication = new ApplicationDTO();
        newObservation = new ObservationDTO();
        client = ClientBuilder.newClient();
    }

    ////////////////////////////////////////////////////////STUDENTS/////////////////////////////////////////////////
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
            FacesExceptionHandler.handleException(e,
                    "Erro inesperado no createStudent do AdministratorManager",
                    component, logger);
            e.printStackTrace();
            return null;
        }
        return "admin_index?faces-redirect=true";
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
            FacesExceptionHandler.handleException(e, "Erro inesperado no "
                    + "getAllStudents AdministratorManager",
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
            FacesExceptionHandler.handleException(e, "Unexpected error! "
                    + "Try again latter!", logger);
            return null;
        }

        return "admin_index?faces-redirect=true";
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

    ////////////////////////////////////////////////////TEACHERS///////////////////////////////////////////////////
    public String createTeacher() {

        try {
            teacherBean.create(
                    newTeacher.getUsername(),
                    newTeacher.getPassword(),
                    newTeacher.getName(),
                    newTeacher.getEmail());
            newTeacher.reset();

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado no "
                    + "createTeacher do AdministratorManager", component, logger);
            e.printStackTrace();
            return null;
        }
        return "admin_index?faces-redirect=true";
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
            FacesExceptionHandler.handleException(e, "Erro inesperado no "
                    + "getAllTeachers AdministratorManager",
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
                
                for (String teachersListUsername : currentProject.getTeachersListUsernames()) {
                    if (teachersListUsername.equals(teacherDTO.getUsername())){
                        returnedTeachers.remove(teacherDTO);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no "
                    + "getAllTeachers AdministratorManager",
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

        return "admin_index?faces-redirect=true";
    }

    ////////////////////////////////////////////// PROJECT PROPOSAL //////////////////////////////////////////////////////
    public void createProjectProposal() throws IOException {

        try {

            logger.info(scientificAreasString);

            projectProposalBean.create(
                    newProjectProposal.getCode(), newProjectProposal.getProjectTypeString(),
                    newProjectProposal.getTitle(),
                    newProjectProposal.getScientificAreas(),
                    newProjectProposal.getProponentUsername(),
                    newProjectProposal.getProjectAbstract(),
                    newProjectProposal.getObjectives(),
                    newProjectProposal.getBibliography(),
                    newProjectProposal.getWorkPlan(),
                    newProjectProposal.getWorkPlace(),
                    newProjectProposal.getSuccessRequirements(),
                    newProjectProposal.getBudget(),
                    newProjectProposal.getSupports());

            if (userManager.isUserInRole(UserGroup.GROUP.Teacher)) {
                redirect("teacher_index");
            }
            redirect("institution_index");

        } catch (EntityAlreadyExistsException | EntityDoesNotExistException
                | MyConstraintViolationException e) {
            FacesExceptionHandler.handleException(e, "Error Creating Project Proposal!",
                    component, logger);
        }

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

    public List<ProjectProposalDTO> getAllProjectProposals(String path) {
        List<ProjectProposalDTO> returnedProjectProposals = null;
        try {
            returnedProjectProposals = client.target(baseUri)
                    .path("/projectProposals/")
                    .path(path)
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ProjectProposalDTO>>() {
                    });

            //returnedProjectProposals = projectProposalBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado: " + e.getMessage(), logger);

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

    public void removeProjectProposal(ActionEvent event) {

        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("projectProposalCode");
            int code = (int) param.getValue();
            projectProposalBean.remove(code);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), logger);
        }

    }

    public void updateProjectProposal() throws IOException {
        try {
            client.target(baseUri)
                    .path("projectProposals/update")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentProjectProposal));

            if (userManager.isUserInRole(UserGroup.GROUP.Teacher)) {
                redirect("teacher_index");
            }
            redirect("institution_index");

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!", logger);
        }

    }

    public List<ProjectProposalDTO> getSearchProjectProposal(String condition) {
        try {
            List<ProjectProposalDTO> foundProjectProposals = projectProposalBean.search(searchableProjectProposal, condition);
            return foundProjectProposals;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error on getSearchProjectProposal()!", logger);
            return null;
        }
    }

    public void redirect(String to) throws IOException {
        if ("update".equals(to)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/proponent/project_proposals_update.xhtml");
        }
        if ("details".equals(to)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/proponent/project_proposals_details.xhtml");
        }
        if ("search".equals(to)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/proponent/search_project_proposal.xhtml");
        }
        if ("teacher_index".equals(to)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/teacher/teacher_index.xhtml");
        }
        if ("institution_index".equals(to)) {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/instituition/institution_index.xhtml");
        }
    }

    public String addObservation() {
        try {
            projectProposalBean.updateProjectProposalState(
                    currentProjectProposal.getCode(),
                    newObservation.getProjectProposalState());

            observationBean.create(newObservation.getMessage(),
                    newObservation.getProjectProposalState().toString(),
                    currentProjectProposal.getCode(), userManager.getUsername());

            return "admin_index?faces-redirect=true";
        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return null;
        }
    }

    public List<ObservationDTO> getAllObservations() {
        try {
            return client.target(URILookup.getBaseAPI()).
                    path("/projectProposals").
                    path(String.valueOf(currentProjectProposal.getCode())).
                    path("/observations").
                    request(MediaType.APPLICATION_XML).
                    get(new GenericType<List<ObservationDTO>>() {
                    });
        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Unexpected error! " + e.getMessage(),
                    logger);
            return null;
        }
    }

    //////////////////////////////////////////////// PUBLIC TEST ////////////////////////////////////////////////////
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
            FacesExceptionHandler.handleException(e, e.getMessage(), component, logger);
            return null;
        }
        return "admin_index?faces-redirect=true";
    }

    public List<PublicTestDTO> getAllPublicTests() {
        try {
            return client.target(URILookup.getBaseAPI()).
                    path("/publictests/all").
                    request(MediaType.APPLICATION_XML).
                    get(new GenericType<List<PublicTestDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!",
                    logger);
            return null;
        }
    }

    public List<PublicTestDTO> getSearchPublicTest() {
        try {
            return client.target(URILookup.getBaseAPI()).
                    path("/publictests/search").
                    path(searchablePublicTest).
                    request(MediaType.APPLICATION_XML).
                    get(new GenericType<List<PublicTestDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error no "
                    + "getSearchPublicTest!", logger);
            return null;
        }
    }

    public String updatePublicTest() {
        try {
            client.target(URILookup.getBaseAPI())
                    .path("/publictests/updateREST")
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentPublicTest));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! "
                    + "Try again latter!", logger);
            return null;
        }
        return "admin_index?faces-redirect=true";
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
            if (uploadManager.getFile().getSize() != 0) {
                DocumentDTO document = new DocumentDTO(uploadManager.getCompletePathFile(),
                        uploadManager.getFilename(),
                        uploadManager.getFile().getContentType());

                UIParameter param = (UIParameter) component.findComponent("publicTestCode2");
                String code = param.getValue().toString();

                client.target(URILookup.getBaseAPI())
                        .path("/publictests/addFileRecord")
                        .path(code)
                        .request(MediaType.APPLICATION_XML)
                        .put(Entity.xml(document));

            }
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro ao fazer o upload do ficheiro!", logger);
        }
    }

    public void removeFileRecord(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("publicTestCode3");
            int code = Integer.parseInt(param.getValue().toString());
            publicTestBean.removeFileRecord(code);
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

    //////////////////////////////////////////////////////////INSTITUTIONS///////////////////////////////////////////////////
    public String createInstitution() {

        try {
            institutionBean.create(
                    newInstitution.getUsername(),
                    newInstitution.getPassword(),
                    newInstitution.getName(),
                    newInstitution.getEmail());
            newInstitution.reset();

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado no "
                    + "createInstitution do AdministratorManager", component, logger);
            e.printStackTrace();
            return null;
        }
        return "admin_index?faces-redirect=true";
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
            FacesExceptionHandler.handleException(e, "Erro inesperado no "
                    + "getAllTeachers AdministratorManager",
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

        return "admin_index?faces-redirect=true";
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

    ////////////////////////////////////////////////////////COURSES//////////////////////////////////////////////////////
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
            FacesExceptionHandler.handleException(e, "Unexpected error! "
                    + "Try again latter!", component, logger);
            return null;
        }
        return "index?faces-redirect=true";
    }

    public List<CourseDTO> getAllCourses() {
        try {
            return courseBean.getAll();
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! "
                    + "Try again latter!", logger);
            return null;
        }
    }

    ////////////////////////////////////////////APPLICATIONS///////////////////////////////////////////////////////////////////
    public void createApplication(UIComponent component) throws IOException {

        //ir buscar o username do estudante via userManager
        String username = userManager.getUsername();

        //ir buscar a projectProposal via codigo
        UIParameter param = (UIParameter) component.findComponent("code");
        int code = Integer.parseInt(param.getValue().toString());

        try {
            List<DocumentDTO> documents = new LinkedList<>();

            List<UploadedFile> files = uploadManager.getFiles();

            for (int i = 0; i < files.size(); i++) {
                documents.add(new DocumentDTO(
                        uploadManager.getCompletePathFiles().get(i),
                        uploadManager.getFilenames().get(i),
                        files.get(i).getContentType()
                ));
            }
            //cria a candidatura e receber o id gerado automaticamente
            Long applicationId = applicationBean.create(
                    username,
                    code,
                    newApplication.getApplyingMessage());
            /*
            client.target(URILookup.getBaseAPI())
                    .path("/applications/addFileRecord")
                    .path(String.valueOf(applicationId))
                    .request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(documents));*/
            applicationBean.addFileRecord(String.valueOf(applicationId),
                    documents);

            //fazer reset ao array de ficheiros
            uploadManager.resetFilesArray();

            newApplication.reset();

            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/student/student_index.xhtml");

        } catch (ApplicationStateException e) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),
                            "candidaturas"));

        } catch (ApplicationNumberException e) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),
                            "candidaturas"));

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e,
                    "Erro inesperado no creeateApplication do AdministratorManager", component, logger);
            e.printStackTrace();
        }
        // return "student/student_index?faces-redirect=true";
    }

    public Collection<ApplicationDTO> getAllStudentApplications() {

        Collection<ApplicationDTO> applications = null;

        try {
            //vai buscar o estudante correntemente logado atraves do username
            String username = userManager.getUsername();

            applications = applicationBean.getStudentApplications(username);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado", component, logger);

        }
        return applications;

    }

    public List<ApplicationDTO> getAllProjectProposalApplicants() {
        try {
            return client.target(URILookup.getBaseAPI()).
                    path("/applications/allApplicants").
                    path(String.valueOf(currentProjectProposal.getCode())).
                    request(MediaType.APPLICATION_XML).
                    get(new GenericType<List<ApplicationDTO>>() {
                    });
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again latter!",
                    logger);
            return null;
        }
    }

    public String updateApplication() {

        try {
            applicationBean.update(currentApplication);

        } catch (ApplicationStateException e) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(),
                            "candidaturas"));

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Unexpected error no "
                    + "updateApplication do AdministratorManager!", logger);
            return null;
        }

        return "student_index?faces-redirect=true";

    }

    public String updateApplicationRest() {

        try {
            WebTarget path = client.target(baseUri)
                    .path("/applications/update");
            System.out.println(path.getUri());
            path.request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentApplication));

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Unexpected error no "
                    + "updateApplication do AdministratorManager!", logger);
            return null;
        }

        return "student_index?faces-redirect=true";

    }

    public void approveApplication(ActionEvent event) throws IOException {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("applicationId");
            String id = param.getValue().toString();

            applicationBean.approveApplication(Long.parseLong(id));
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado, tente mais tarde.", logger);
        }
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("http://localhost:8080/MasterManager-war/faces/admin/admin_index.xhtml");
    }
    
    /////////////////////////////////////////// PROJECTS //////////////////////////////////////////
    
    public List<ProjectDTO> getAllProjects() {
        List<ProjectDTO> returnedProjects = null;
        try {
            returnedProjects = client.target(baseUri)
                    .path("/project/all")
                    .request(MediaType.APPLICATION_XML)
                    .get(new GenericType<List<ProjectDTO>>() {
                    });

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllTeachers AdministratorManager",
                    logger);

        }
        return returnedProjects;
    }
    
    public String enrollTeacher() {
        try {
            projectBean.enrollTeacher(orientadores, currentProject.getId());
        }  catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Erro inesperado no getAllTeachers AdministratorManager",
                    logger);
        }
        
        return "admin_index?faces-redirect=true";
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

    public String getSearchableProjectProposal() {
        return searchableProjectProposal;
    }

    public void setSearchableProjectProposal(String searchableProjectProposal) {
        this.searchableProjectProposal = searchableProjectProposal;
    }

    public ProjectType[] getAllProjectTypes() {
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

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public ApplicationDTO getNewApplication() {
        return newApplication;
    }

    public void setNewApplication(ApplicationDTO newApplication) {
        this.newApplication = newApplication;
    }

    public ObservationDTO getNewObservation() {
        return newObservation;
    }

    public void setNewObservation(ObservationDTO newObservation) {
        this.newObservation = newObservation;
    }

    public ProjectProposalState[] getAllProposalValidationStates() {
        return new ProjectProposalState[]{ProjectProposalState.PENDING,
            ProjectProposalState.ACCEPTED, ProjectProposalState.NOT_ACCEPTED};
    }

    public ApplicationDTO getCurrentApplication() {
        return currentApplication;
    }

    public void setCurrentApplication(ApplicationDTO currentApplication) {
        this.currentApplication = currentApplication;
    }

    public ProjectDTO getCurrentProject() {
        return currentProject;
    }

    public void setCurrentProject(ProjectDTO currentProject) {
        this.currentProject = currentProject;
    }

    public List<String> getOrientadores() {
        return orientadores;
    }

    public void setOrientadores(List<String> orientadores) {
        this.orientadores = orientadores;
    }
}
