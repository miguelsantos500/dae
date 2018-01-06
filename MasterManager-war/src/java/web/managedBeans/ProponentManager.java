/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.managedBeans;

import dtos.ProjectProposalDTO;
import ejbs.ProjectProposalBean;
import entities.project.ProjectType;
import entities.users.UserGroup;
import exceptions.EntityAlreadyExistsException;
import exceptions.EntityDoesNotExistException;
import exceptions.MyConstraintViolationException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import web.FacesExceptionHandler;
import web.UserManager;

/**
 *
 * @author Miguel
 */
@ManagedBean
@SessionScoped
public class ProponentManager {

    /**
     * ** Beans ***
     */
    @EJB
    private ProjectProposalBean projectProposalBean;

    /**
     * ** newObjects ***
     */
    private ProjectProposalDTO newProjectProposal;

    /**
     * ** currentObjects ***
     */
    private ProjectProposalDTO currentProjectProposal;

    /**
     * ***Searchable objects******
     */
    private String searchableProjectProposal;

    /**
     * ** Other ***
     */
    private static final Logger LOGGER = Logger.getLogger("web.managedBeans.ProponentManager");
    private UIComponent component;

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;
    private Client client;
    private final String baseUri
            = "http://localhost:8080/MasterManager-war/webapi";

    /**
     * Creates a new instance of ProponentManager
     */
    public ProponentManager() {
        newProjectProposal = new ProjectProposalDTO();
        client = ClientBuilder.newClient();
    }

    public void createProjectProposal() throws IOException {

        try {

            projectProposalBean.create(
                    newProjectProposal.getCode(), 
                    newProjectProposal.getProjectTypeString(),
                    newProjectProposal.getTitle(),
                    newProjectProposal.getScientificAreas(),
                    userManager.getUsername(),
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
                    component, LOGGER);
        }
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

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado", component, LOGGER);

        }
        return returnedProjectProposals;
    }

    public void updateProjectProposal() {
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
            FacesExceptionHandler.handleException(e, "Unexpected error! " + 
                    e.getMessage(), LOGGER);
        }
    }

    public void removeProjectProposal(ActionEvent event) {

        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("projectProposalCode");
            int code = (int) param.getValue();
            projectProposalBean.remove(code);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), LOGGER);
        }

    }

    public List<ProjectProposalDTO> getSearchProjectProposal(String condition) {
        try {
            List<ProjectProposalDTO> foundProjectProposals = 
                    projectProposalBean.search(searchableProjectProposal, condition);
            return foundProjectProposals;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e,
                    "Unexpected error on getSearchProjectProposal()! "
                    + e.getMessage(), LOGGER);
            return null;
        }
    }

    public void redirect(String to) throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if ("update".equals(to)) {
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/proponent/project_proposals_update.xhtml");
        }
        if ("details".equals(to)) {
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/proponent/project_proposals_details.xhtml");
        }
        if ("search".equals(to)) {
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/proponent/search_project_proposal.xhtml");
        }
        if ("teacher_index".equals(to)) {
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/teacher/teacher_index.xhtml");
        }
        if ("institution_index".equals(to)) {
            externalContext.redirect("http://localhost:8080/MasterManager-war/faces/instituition/institution_index.xhtml");
        }
    }

    public ProjectProposalBean getProjectProposalBean() {
        return projectProposalBean;
    }

    public void setProjectProposalBean(ProjectProposalBean projectProposalBean) {
        this.projectProposalBean = projectProposalBean;
    }

    public ProjectProposalDTO getNewProjectProposal() {
        return newProjectProposal;
    }

    public void setNewProjectProposal(ProjectProposalDTO newProjectProposal) {
        this.newProjectProposal = newProjectProposal;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public ProjectProposalDTO getCurrentProjectProposal() {
        return currentProjectProposal;
    }

    public void setCurrentProjectProposal(ProjectProposalDTO currentProjectProposal) {
        this.currentProjectProposal = currentProjectProposal;
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

}
