/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.managedBeans;

import static com.sun.xml.ws.security.addressing.impl.policy.Constants.logger;
import dtos.ApplicationDTO;
import dtos.DocumentDTO;
import ejbs.ApplicationBean;
import ejbs.PublicTestBean;
import exceptions.ApplicationNumberException;
import exceptions.EntityDoesNotExistException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
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
import org.primefaces.model.DefaultUploadedFile;
import org.primefaces.model.UploadedFile;
import util.URILookup;
import web.FacesExceptionHandler;
import web.UploadManager;
import web.UploadManager;
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
    private boolean replaceCV;
    private boolean replacePL;
    private boolean replaceCert;

    @ManagedProperty(value = "#{userManager}")
    private UserManager userManager;

    @ManagedProperty(value = "#{uploadManager}")
    private UploadManager uploadManager;

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

            FacesExceptionHandler.handleException(e, "Não existem candidatura!" + e.getMessage(), LOGGER);

        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro inesperado: " + e.getMessage(), LOGGER);

        }
        return applications;

    }

    public String updateApplication() {

        try {

            /*
            WebTarget path = client.target(baseUri)
                    .path("/applications/update");
            System.out.println(path.getUri());
            path.request(MediaType.APPLICATION_XML)
                    .put(Entity.xml(currentApplication));
             */
            uploadManager.resetFilesArray();
            
            DocumentDTO[] documents = new DocumentDTO[3];
            
            if(replaceCV){
                uploadManager.uploadSpecificFile(uploadManager.getFileCv());
                String path = uploadManager.getCompletePathFiles().get(0);
                String name = uploadManager.getFilenames().get(0);
                
                documents[0] = new DocumentDTO(path, name, 
                        uploadManager.getFileCv().getContentType());
                
                uploadManager.resetFilesArray();
            }else{
                documents[0] = null;
            }
            
            if(replacePL){
                uploadManager.uploadSpecificFile(uploadManager.getFilePresentationLetter());
                
                String path = uploadManager.getCompletePathFiles().get(0);
                String name = uploadManager.getFilenames().get(0);
                
                documents[1] = new DocumentDTO(path, name, 
                        uploadManager.getFilePresentationLetter().getContentType());
                
                uploadManager.resetFilesArray();
            }else{
                documents[1] = null;
            }
            
            if(replaceCert){
                uploadManager.uploadSpecificFile(uploadManager.getFileCertificate());
                
                String path = uploadManager.getCompletePathFiles().get(0);
                String name = uploadManager.getFilenames().get(0);
                
                documents[2] = new DocumentDTO(path, name, 
                        uploadManager.getFileCertificate().getContentType());
                
                uploadManager.resetFilesArray();
                
            }else{
                documents[2] = null;
            }

            applicationBean.update(currentApplication, documents);

            replaceCV = false;
            replacePL = false;
            replaceCert = false;

        } catch (Exception e) {
            e.printStackTrace();
            FacesExceptionHandler.handleException(e,
                    "Unexpected error on updateApplication StudentManager! "
                    + e.getMessage(), LOGGER);
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
            FacesExceptionHandler.handleException(e, "Unexpected error! Try again later! " + e.getMessage(), LOGGER);
        }
    }

    public void uploadFileRecord(UIComponent component) {
        try {
            if (uploadManager.getFile().getSize() != 0) {
                DocumentDTO document = new DocumentDTO(uploadManager.getCompletePathFile(),
                        uploadManager.getFilename(),
                        uploadManager.getFile().getContentType());

                UIParameter param = (UIParameter) component.findComponent("appIdNewCv");
                String appId = param.getValue().toString();

                client.target(URILookup.getBaseAPI())
                        .path("/applications/addFileRecord")
                        .path(appId)
                        .request(MediaType.APPLICATION_XML)
                        .put(Entity.xml(document));

            }
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Erro ao fazer o upload do ficheiro!", logger);
        }
    }

    public void removeCv(ActionEvent event) {

        UIParameter param = (UIParameter) event.getComponent().findComponent("applicationIdCv");
        Long id = Long.parseLong(param.getValue().toString());

        param = (UIParameter) event.getComponent().findComponent("nameCv");
        String name = (String) param.getValue();

        param = (UIParameter) event.getComponent().findComponent("indexCv");
        int index = Integer.parseInt(param.getValue().toString());

        removeDocument(id, index);

    }

    public void removePresentationLetter(ActionEvent event) {
        UIParameter param = (UIParameter) event.getComponent().findComponent("applicationIdRemovePL");
        Long id = Long.parseLong(param.getValue().toString());

        param = (UIParameter) event.getComponent().findComponent("namePL");
        String name = (String) param.getValue();

        param = (UIParameter) event.getComponent().findComponent("indexPL");
        int index = Integer.parseInt(param.getValue().toString());

        removeDocument(id, index);
    }

    public void removeCertificate(ActionEvent event) {
        UIParameter param = (UIParameter) event.getComponent().findComponent("applicationIdRemoveCert");
        Long id = Long.parseLong(param.getValue().toString());

        param = (UIParameter) event.getComponent().findComponent("nameCert");
        String name = (String) param.getValue();

        param = (UIParameter) event.getComponent().findComponent("indexCert");
        int index = Integer.parseInt(param.getValue().toString());

        removeDocument(id, index);
    }

    public void removeDocument(Long id, int index) {

        try {
            applicationBean.removeFileRecord(id, index);
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Unexpected error on removeDocumentApplication! "
                    + "Try again later!", logger);
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

    public UploadManager getUploadManager() {
        return uploadManager;
    }

    public void setUploadManager(UploadManager uploadManager) {
        this.uploadManager = uploadManager;
    }

    public boolean isReplaceCV() {
        return replaceCV;
    }

    public void setReplaceCV(boolean replaceCV) {
        this.replaceCV = replaceCV;
    }

    public boolean isReplacePL() {
        return replacePL;
    }

    public void setReplacePL(boolean replacePL) {
        this.replacePL = replacePL;
    }

    public boolean isReplaceCert() {
        return replaceCert;
    }

    public void setReplaceCert(boolean replaceCert) {
        this.replaceCert = replaceCert;
    }

}
