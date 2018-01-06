/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.project;

import entities.Application;
import entities.users.Proponent;
import entities.users.Student;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import static javax.persistence.EnumType.STRING;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "PROJECT_PROPOSALS")
@NamedQueries({
    @NamedQuery(name = "getAllProjectProposals",
        query = "SELECT p FROM ProjectProposal p"),
    @NamedQuery(name = "getAllProjectProposalsFinished",
        query = "SELECT p FROM ProjectProposal p WHERE p.projectProposalState = :state"),
}) 
public class ProjectProposal implements Serializable {

    @Id
    private int code;

    @NotNull
    @Enumerated(STRING)
    private ProjectType projectType;

    @NotNull
    private String title;

    @NotNull
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> scientificAreas;

    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "PROPONENT_CODE")
    private Proponent proponent;
    
    @NotNull
    private String projectAbstract;

    @NotNull
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> objectives;

    //No máximo 5.
    @NotNull
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> bibliography;

    @NotNull
    private String workPlan;

    @NotNull
    private String workPlace;

    @NotNull
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> successRequirements;

    @NotNull
    private String budget;

    @NotNull
    @ElementCollection(fetch=FetchType.EAGER)
    private List<String> supports;
    
    @NotNull
    @Enumerated(STRING)
    private ProjectProposalState projectProposalState;
    
    
    @OneToMany(mappedBy="projectProposal", cascade=CascadeType.REMOVE)
    private List<Observation> observations;
    
    @OneToMany(mappedBy="projectProposal", cascade=CascadeType.REMOVE)
    private List<Application> applications;
    
    @OneToOne(mappedBy="projectProposal", cascade=CascadeType.REMOVE)
    private Project project;
    

    public ProjectProposal() {
        this.applications = new LinkedList<>();
    }

    public ProjectProposal(int code, ProjectType projectType,
            String title, List<String> scientificAreas, Proponent proponent,
            String projectAbstract, List<String> objectives,
            List<String> bibliography, String workPlan,
            String workPlace, List<String> successRequirements,
            String budget, List<String> supports) {

        this.code = code;
        this.projectType = projectType;
        this.title = title;
        this.scientificAreas = scientificAreas;
        this.proponent = proponent;
        this.projectAbstract = projectAbstract;
        this.objectives = objectives;
        this.bibliography = bibliography;
        this.workPlan = workPlan;
        this.workPlace = workPlace;
        this.successRequirements = successRequirements;
        this.budget = budget;
        this.supports = supports;
        this.projectProposalState = ProjectProposalState.ACCEPTED;
        this.applications = new LinkedList<>();
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

    public List<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }

    public List<String> getBibliography() {
        return bibliography;
    }

    public void setBibliography(List<String> bibliography) {
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

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public List<String> getSupports() {
        return supports;
    }

    public void setSupports(List<String> supports) {
        this.supports = supports;
    }

    public ProjectProposalState getProjectProposalState() {
        return projectProposalState;
    }

    public void setProjectProposalState(ProjectProposalState projectProposalState) {
        this.projectProposalState = projectProposalState;
    }

    public void removeApplication(Application app){
        applications.remove(app);
    }
    
    public void addApplication(Application application){
        applications.add(application);
    }

    public List<Observation> getObservations() {
        return observations;
    }

    public void setObservations(List<Observation> observations) {
        this.observations = observations;
    }

    public void removeObservation(Observation observation){
        observations.remove(observation);
    }
    
    public void addObservation(Observation observation){
        observations.add(observation);
    }
    
    @Override
    public String toString() {
        return "entities.ProjectProposal[ id=" + code + " ]";
    }

}
