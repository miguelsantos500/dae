package dtos;

import entities.project.ProjectType;
import entities.users.Proponent;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProjectProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectProposalDTO implements Serializable{


    private int code;
    
    private String projectTypeString;
    
    private String title;
    
    private String proponentUsername;
    
    private String projectAbstract;
    
    private String workPlan;

    private String workPlace;
    
    private String budget;
    
    private List<String> scientificAreas;
    
    
    
    public ProjectProposalDTO(){
    }

    public ProjectProposalDTO(int code, String projectTypeString, String title, 
            String proponentUsername, String projectAbstract, String workPlan, 
            String workPlace, String budget, List<String> scientificAreas) {
        this.code = code;
        this.projectTypeString = projectTypeString;
        this.title = title;
        this.proponentUsername = proponentUsername;
        this.projectAbstract = projectAbstract;
        this.workPlan = workPlan;
        this.workPlace = workPlace;
        this.budget = budget;
        this.scientificAreas = scientificAreas;
    }


    
    
    
    public void reset(){
        this.code = 0;
        this.title = null;
        this.projectAbstract = null;
        this.workPlan = null;
        this.workPlace = null;
        this.budget = null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProjectAbstract() {
        return projectAbstract;
    }

    public void setProjectAbstract(String projectAbstract) {
        this.projectAbstract = projectAbstract;
    }

    public String getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public List<String> getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(List<String> scientificAreas) {
        this.scientificAreas = scientificAreas;
    }

    public String getProponentUsername() {
        return proponentUsername;
    }

    public void setProponentUsername(String proponentUsername) {
        this.proponentUsername = proponentUsername;
    }

    public String getProjectTypeString() {
        return projectTypeString;
    }

    public void setProjectTypeString(String projectTypeString) {
        this.projectTypeString = projectTypeString;
    }

    
    
    
    
}
