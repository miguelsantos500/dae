package dtos;

import entities.Document;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDTO {

    private Long id;
    
    private List<String> teachersListNames;
    private List<String> teachersListUsernames;
    private String teachers;

    private String messageToTeacher;

    private int projectProposalCode;
    private String projectProposalTitle;

    private Document articles;
    private Document sourceCode;
    private Document assignment;
    private Document other;

    private String documentsDescription;
    
    private String studentUsername;
    private String studentName;
    
    public ProjectDTO() {
        this.teachersListNames = new LinkedList<>();
        this.teachersListUsernames = new LinkedList<>();
    }

    public ProjectDTO(Long id, String messageToTeacher, 
                    String projectProposalTitle, int projectProposalCode, 
                    String studentName, String studentUsername, String teachers) {
        this.id = id;
        this.studentUsername = studentUsername;
        this.studentName = studentName;
        this.projectProposalCode = projectProposalCode;
        this.projectProposalTitle = projectProposalTitle;
        this.messageToTeacher = messageToTeacher;
        this.teachers = teachers;
        this.teachersListNames = new LinkedList<>();
        this.teachersListUsernames = new LinkedList<>();
    }
    
    public void reset() {
        studentUsername = null;
        studentName = null;
        messageToTeacher = null;
        documentsDescription = null;
        projectProposalTitle = null;
        projectProposalTitle = null;
        teachers = null;
        this.teachersListNames = new LinkedList<>();
        this.teachersListUsernames = new LinkedList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageToTeacher() {
        return messageToTeacher;
    }

    public void setMessageToTeacher(String messageToTeacher) {
        this.messageToTeacher = messageToTeacher;
    }

    public Document getArticles() {
        return articles;
    }

    public void setArticles(Document articles) {
        this.articles = articles;
    }

    public Document getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(Document sourceCode) {
        this.sourceCode = sourceCode;
    }

    public Document getAssignment() {
        return assignment;
    }

    public void setAssignment(Document assignment) {
        this.assignment = assignment;
    }

    public Document getOther() {
        return other;
    }

    public void setOther(Document other) {
        this.other = other;
    }

    public String getDocumentsDescription() {
        return documentsDescription;
    }

    public void setDocumentsDescription(String documentsDescription) {
        this.documentsDescription = documentsDescription;
    }

    public int getProjectProposalCode() {
        return projectProposalCode;
    }

    public void setProjectProposalCode(int projectProposalCode) {
        this.projectProposalCode = projectProposalCode;
    }

    public String getProjectProposalTitle() {
        return projectProposalTitle;
    }

    public void setProjectProposalTitle(String projectProposalTitle) {
        this.projectProposalTitle = projectProposalTitle;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTeachers() {
        return teachers;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public List<String> getTeachersListNames() {
        return teachersListNames;
    }

    public void setTeachersListNames(List<String> teachersListNames) {
        this.teachersListNames = teachersListNames;
    }

    public List<String> getTeachersListUsernames() {
        return teachersListUsernames;
    }

    public void setTeachersListUsernames(List<String> teachersListUsernames) {
        this.teachersListUsernames = teachersListUsernames;
    }

    public void addToListTeacherName(String name) {
        this.teachersListNames.add(name);
    }
    
    public void addToListTeacherUsername(String username) {
        this.teachersListUsernames.add(username);
    }
}
