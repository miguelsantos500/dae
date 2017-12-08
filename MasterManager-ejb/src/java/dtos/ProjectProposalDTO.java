package dtos;

import entities.project.ProjectType;
import entities.users.Proponent;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import entities.users.User;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProjectProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectProposalDTO implements Serializable{


    private int code;
    
    private ProjectType projectType;
    
    private String title;
    
    private Proponent proponent;
    
    private String projectAbstract;
    
    private String workPlan;

    private String workPlace;
    
    private String budget;
    
    private List<String> scientificAreas;
    
    
    
    public ProjectProposalDTO(){
    }

    public ProjectProposalDTO(int code, ProjectType projectType, String title,
            Proponent proponent, String projectAbstract, 
            String workPlan, String workPlace, 
            String budget, List<String> scientificAreas) {
        this.code = code;
        this.projectType = projectType;
        this.title = title;
        this.proponent = proponent;
        this.projectAbstract = projectAbstract;
        this.workPlan = workPlan;
        this.workPlace = workPlace;
        this.budget = budget;
        this.scientificAreas = scientificAreas;
    }
    
    
    
    public void reset(){
        this.code = 0;
        this.projectType = null;
        this.title = null;
        this.proponent = null;
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

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Proponent getProponent() {
        return proponent;
    }

    public void setProponent(Proponent proponent) {
        this.proponent = proponent;
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

    
    
    
}
