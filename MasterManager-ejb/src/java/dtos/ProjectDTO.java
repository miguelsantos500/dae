package dtos;

import entities.Document;
import entities.project.ProjectProposal;
import entities.users.Student;
import entities.users.Teacher;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Project")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProjectDTO {

    private Long id;

    private Teacher[] teachers;

    private String messageToTeacher;

    private ProjectProposal projectProposal;

    private Document articles;
    private Document sourceCode;
    private Document assignment;
    private Document other;

    private String documentsDescription;

    private Student student;
    
    public ProjectDTO() {
    }

    public ProjectDTO(Long id, Teacher[] teachers, String messageToTeacher, 
                    ProjectProposal projectProposal, Student student, String docDescription) {
        this.id = id;
        this.student = student;
        this.projectProposal = projectProposal;
        this.messageToTeacher = messageToTeacher;
        this.documentsDescription = docDescription;
        this.teachers = teachers;
    }
    
    public void reset() {
        student = null;
        projectProposal = null;
        messageToTeacher = null;
        documentsDescription = null;
        teachers = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Teacher[] getTeachers() {
        return teachers;
    }

    public void setTeachers(Teacher[] teachers) {
        this.teachers = teachers;
    }

    public String getMessageToTeacher() {
        return messageToTeacher;
    }

    public void setMessageToTeacher(String messageToTeacher) {
        this.messageToTeacher = messageToTeacher;
    }

    public ProjectProposal getProjectProposal() {
        return projectProposal;
    }

    public void setProjectProposal(ProjectProposal projectProposal) {
        this.projectProposal = projectProposal;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    
}
