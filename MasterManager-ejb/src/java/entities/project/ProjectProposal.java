/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import users.User;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "PROJECT_PROPOSALS")
@NamedQuery(name = "getAllProjectProposals",
        query = "SELECT p FROM ProjectProposal p") /*TODO: ORDER BY*/
public class ProjectProposal implements Serializable {

    @Id
    private int code;
    
    @NotNull
    private ProjectType projectType;
    
    @NotNull
    private String title;
    
    @NotNull
    private List<String> scientificAreas;
    
    
    //Quem propos a proposta
    //TODO: Classe que é super, ou interface, de quem pode Propor, ao invés de User?
    @NotNull
    @ManyToOne
    @JoinColumn(name = "PREPONENT_CODE")
    private User preponent;
    
    @NotNull
    private String projectAbstract;
    
    @NotNull
    private List<String> objectives;
    
    //ArrayList porque é no máximo 5.
    @NotNull
    private ArrayList<String> bibliography;
    
    @NotNull
    private String workPlan;
    
    @NotNull
    private String workPlace;
    
    @NotNull
    private List<String>  successRequirements;
    
    @NotNull
    private Budget budget;
    
    @NotNull
    private List<String> supports;

    public ProjectProposal() {
    }
    

    public ProjectProposal(int code, ProjectType projectType, 
            String title, List<String> scientificAreas, User preponent, 
            String projectAbstract, List<String> objectives, 
            ArrayList<String> bibliography, String workPlan, 
            String workPlace, List<String> successRequirements, 
            Budget budget, List<String> supports) {
        
        this.code = code;
        this.projectType = projectType;
        this.title = title;
        this.scientificAreas = scientificAreas;
        this.preponent = preponent;
        this.projectAbstract = projectAbstract;
        this.objectives = objectives;
        this.bibliography = bibliography;
        this.workPlan = workPlan;
        this.workPlace = workPlace;
        this.successRequirements = successRequirements;
        this.budget = budget;
        this.supports = supports;
    }

    
    
    
    
    
    //GETTERS AND SETTERS
    
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

    public List<String> getScientificAreas() {
        return scientificAreas;
    }

    public void setScientificAreas(List<String> scientificAreas) {
        this.scientificAreas = scientificAreas;
    }

    public User getPreponent() {
        return preponent;
    }

    public void setPreponent(User preponent) {
        this.preponent = preponent;
    }

    public String getProjectAbstract() {
        return projectAbstract;
    }

    public void setProjectAbstract(String projectAbstract) {
        this.projectAbstract = projectAbstract;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }

    public ArrayList<String> getBibliography() {
        return bibliography;
    }

    public void setBibliography(ArrayList<String> bibliography) {
        this.bibliography = bibliography;
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

    public List<String> getSuccessRequirements() {
        return successRequirements;
    }

    public void setSuccessRequirements(List<String> successRequirements) {
        this.successRequirements = successRequirements;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public List<String> getSupports() {
        return supports;
    }

    public void setSupports(List<String> supports) {
        this.supports = supports;
    }
    
    
    
    
    
    
    
    
    
    
    @Override
    public String toString() {
        return "entities.ProjectProposal[ id=" + code + " ]";
    }
    
}