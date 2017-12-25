package dtos;

import entities.project.ProjectProposalState;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ProjectProposal")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectProposalDTO implements Serializable {

    private int code;

    private String projectTypeString;

    private String title;

    private String proponentUsername;

    private String projectAbstract;

    private String workPlan;

    private String workPlace;

    private String budget;

    private List<String> scientificAreas;
    private String scientificAreasString;

    private List<String> objectives;
    private String objectivesString;

    private List<String> bibliography;
    private String bibliographyString;

    private List<String> successRequirements;
    private String successRequirementsString;

    private List<String> supports;
    private String supportsString;
    
    private ProjectProposalState projectProposalState;

    public ProjectProposalDTO() {
    }

    public ProjectProposalDTO(int code, String projectTypeString, String title, 
            String proponentUsername, String projectAbstract, String workPlan, 
            String workPlace, String budget, List<String> scientificAreas, 
            List<String> objectives, List<String> bibliography, 
            List<String> successRequirements, List<String> supports,
            ProjectProposalState projectProposalState) {
        this.code = code;
        this.projectTypeString = projectTypeString;
        this.title = title;
        this.proponentUsername = proponentUsername;
        this.projectAbstract = projectAbstract;
        this.workPlan = workPlan;
        this.workPlace = workPlace;
        this.budget = budget;
        this.scientificAreas = scientificAreas;
        this.objectives = objectives;
        this.bibliography = bibliography;
        this.successRequirements = successRequirements;
        this.supports = supports;
        this.projectProposalState = projectProposalState;
    }

    

    public void reset() {
        this.code = 0;
        this.title = null;
        this.projectAbstract = null;
        this.workPlan = null;
        this.workPlace = null;
        this.budget = null;
    }

    public List<String> stringToList(String string, String separator) {
        String[] stringArray = string.split(separator);
        List<String> stringList = new LinkedList<>();

        for (int i = 0; i < stringArray.length; i++) {
            stringList.add(stringArray[i]);
        }
        return stringList;
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

    
    public List<String> getScientificAreas() {
        if (scientificAreasString != null) {
            return stringToList(scientificAreasString, ";");
        }
        return scientificAreas;
    }

    public void setScientificAreas(List<String> scientificAreas) {
        this.scientificAreas = scientificAreas;
    }
    
    public String getScientificAreasString() {
        return scientificAreasString;
    }

    public void setScientificAreasString(String scientificAreasString) {
        this.scientificAreasString = scientificAreasString;
    }

    public List<String> getObjectives() {
        if (objectivesString != null) {
            return stringToList(objectivesString, ";");
        }
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }

    public String getObjectivesString() {
        return objectivesString;
    }

    public void setObjectivesString(String objectivesString) {
        this.objectivesString = objectivesString;
    }

    public List<String> getBibliography() {
        if (bibliographyString != null) {
            return stringToList(bibliographyString, ";");
        }
        return bibliography;
    }

    public void setBibliography(List<String> bibliography) {
        this.bibliography = bibliography;
    }

    public String getBibliographyString() {
        return bibliographyString;
    }

    public void setBibliographyString(String bibliographyString) {
        this.bibliographyString = bibliographyString;
    }

    public List<String> getSuccessRequirements() {
        if (successRequirementsString != null) {
            return stringToList(successRequirementsString, ";");
        }
        return successRequirements;
    }

    public void setSuccessRequirements(List<String> successRequirements) {
        this.successRequirements = successRequirements;
    }

    public String getSuccessRequirementsString() {
        return successRequirementsString;
    }

    public void setSuccessRequirementsString(String successRequirementsString) {
        this.successRequirementsString = successRequirementsString;
    }

    public List<String> getSupports() {
        if (supportsString != null) {
            return stringToList(supportsString, ";");
        }
        return supports;
    }

    public void setSupports(List<String> supports) {
        this.supports = supports;
    }

    public String getSupportsString() {
        return supportsString;
    }

    public void setSupportsString(String supportsString) {
        this.supportsString = supportsString;
    }

    public ProjectProposalState getProjectProposalState() {
        return projectProposalState;
    }

    public void setProjectProposalState(ProjectProposalState projectProposalState) {
        this.projectProposalState = projectProposalState;
    }
    

}
