/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.managedBeans;

import dtos.ApplicationDTO;
import ejbs.ApplicationBean;
import ejbs.PublicTestBean;
import exceptions.ApplicationNumberException;
import exceptions.EntityDoesNotExistException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import web.FacesExceptionHandler;
import web.UserManager;

/**
 *
 * @author Miguel
 */
@ManagedBean
@SessionScoped
public class StudentManager {

    /**
     * ** Beans ***
     */
    @EJB
    private PublicTestBean publicTestBean;
    @EJB
    private ApplicationBean applicationBean;

    /**
     * ** currentObjects ***
     */
    private ApplicationDTO currentApplication;

    /**
     * ***Searchable objects******
     */
    private String searchableApplication;

    /**
     * ** Other ***
     */
    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    private static final Logger LOGGER = Logger.getLogger("web.managedBeans.StudentManager");

    private UIComponent component;

    private Client client;
    private final String baseUri
            = "http://localhost:8080/MasterManager-war/webapi";

    /**
     * Creates a new instance of StudentManager
     */
    public StudentManager() {
        client = ClientBuilder.newClient();
    }

    public List<ApplicationDTO> getSearchApplication() {
        try {
            //tenho que saber qual é o estudante para que ele procure só as candidaturas desse estudante
            String username = userManager.getUsername();
            List<ApplicationDTO> foundApplications = applicationBean.search(searchableApplication, username);
            return foundApplications;
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error on getSearchApplication()!", LOGGER);
            return null;
        }
    }

    public Collection<ApplicationDTO> getAllStudentApplications() {

        Collection<ApplicationDTO> applications = null;

        try {
            //vai buscar o estudante correntemente logado atraves do username
            String username = userManager.getUsername();

            applications = applicationBean.getStudentApplications(username);

        } catch (ApplicationNumberException e) {
            //TODO: apresentar a mensagem que não existe applications
            FacesExceptionHandler.handleException(e, e.getMessage(), LOGGER);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado: " + e.getMessage(), LOGGER);

        }
        return applications;

    }

    public String updateApplication() {

        try {
            WebTarget path = client.target(baseUri)
                    .path("/applications/update");
            System.out.println(path.getUri());
            path.request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentApplication));

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e, "Unexpected error no updateApplication do AdministratorManager!", LOGGER);
            return null;
        }

        return "student_index?faces-redirect=true";

    }

    public void removeApplication(ActionEvent event) {
        try {
            UIParameter param = (UIParameter) event.getComponent().findComponent("applicationId");
            applicationBean.remove(param.getValue().toString());
        } catch (EntityDoesNotExistException e) {
            FacesExceptionHandler.handleException(e, e.getMessage(), LOGGER);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again later! " + e.getMessage(), component, LOGGER);
        }
    }

    //GETTERS AND SETTERS
    public String getSearchableApplication() {
        return searchableApplication;
    }

    public void setSearchableApplication(String searchableApplication) {
        this.searchableApplication = searchableApplication;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
    }

    public ApplicationDTO getCurrentApplication() {
        return currentApplication;
    }

    public void setCurrentApplication(ApplicationDTO currentApplication) {
        this.currentApplication = currentApplication;
    }

}
