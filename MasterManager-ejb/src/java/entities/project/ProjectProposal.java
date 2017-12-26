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
import javax.persistence.Entity;
import static javax.persistence.EnumType.STRING;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Miguel
 */
@Entity
@Table(name = "PROJECT_PROPOSALS")
@NamedQuery(name = "getAllProjectProposals",
        query = "SELECT p FROM ProjectProposal p")
/*TODO: ORDER BY*/
public class ProjectProposal implements Serializable {

    @Id
    private int code;

    @NotNull
    @Enumerated(STRING)
    private ProjectType projectType;

    @NotNull
    private String title;

    @NotNull
    private List<String> scientificAreas;

    //Quem propos a proposta
    //TODO: Classe que é super, ou interface, de quem pode Propor, ao invés de User?
    @NotNull
    @ManyToOne
    @JoinColumn(name = "PROPONENT_CODE")
    private Proponent proponent;

    @OneToMany(mappedBy="projectProposal")
    private List<Student> students;

    @NotNull
    private String projectAbstract;

    @NotNull
    private List<String> objectives;

    //No máximo 5.
    @NotNull
    private List<String> bibliography;

    @NotNull
    private String workPlan;

    @NotNull
    private String workPlace;

    @NotNull
    private List<String> successRequirements;

    /*    
    @NotNull
    private Budget budget;*///TODO:LATER
    @NotNull
    private String budget;

    @NotNull
    private List<String> supports;
    
    @NotNull
    @Enumerated(STRING)
    private ProjectProposalState projectProposalState;
    
    @OneToMany(mappedBy="projectProposal")
    private List<Application> applications;
    

    public ProjectProposal() {
        this.students = new LinkedList<>();
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
        this.students = new LinkedList<>();
        this.projectProposalState = ProjectProposalState.PENDING;
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
    
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    
    @Override
    public String toString() {
        return "entities.ProjectProposal[ id=" + code + " ]";
    }

    public void removeApplication(Application app){
        applications.remove(app);
    }
    

    public void removeStudent(Student student) {
        students.remove(student);
    }

    public void addStudent(Student student) {
        students.add(student);
    }
    
    public void addApplication(Application application){
        applications.add(application);
    }

}
