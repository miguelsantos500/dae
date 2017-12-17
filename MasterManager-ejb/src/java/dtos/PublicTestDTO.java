/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PublicTest")
@XmlAccessorType(XmlAccessType.FIELD)
public class PublicTestDTO implements Serializable {

    private int code;
    private String title;
    private Date testDateTime;
    private String testDateString;
    private String Place;
    private String link;
    private String juryPresidentUsername;
    private String juryPresidentName;
    private String advisorUsername;
    private String advisorName;
    private String outsideTeacherName;
    private String outsideTeacherEmail;
    private String studentUsername;
    private String studentName;
    private String documentName;

    public PublicTestDTO() {
    }

    public PublicTestDTO(int code, String title, Date testDateTime,
            String Place, String link, String juryPresidentUsername, String juryPresidentName, 
            String advisorUsername, String advisorName, String outsideTeacherName, 
            String outsideTeacherEmail, String studentUsername, String studentName,
            String testDateString, String documentName) {
        this.code = code;
        this.title = title;
        this.testDateTime = testDateTime;
        this.Place = Place;
        this.link = link;
        this.juryPresidentUsername = juryPresidentUsername;
        this.juryPresidentName = juryPresidentName;
        this.advisorUsername = advisorUsername;
        this.advisorName = advisorName;
        this.outsideTeacherName = outsideTeacherName;
        this.outsideTeacherEmail = outsideTeacherEmail;
        this.studentUsername = studentUsername;
        this.studentName = studentName;
        this.testDateString = testDateString;
        this.documentName = documentName;
    }
    
    public void reset() {
        this.code = 0;
        this.title = null;
        this.testDateTime = null;
        this.testDateString = null;
        this.Place = null;
        this.link = null;
        this.juryPresidentUsername = null;
        this.advisorUsername = null;
        this.outsideTeacherName = null;
        this.outsideTeacherEmail = null;
        this.studentUsername = null;
        this.documentName = null;
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

    public Date getTestDateTime() {
        return testDateTime;
    }

    public void setTestDateTime(Date testDateTime) {
        this.testDateTime = testDateTime;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String Place) {
        this.Place = Place;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAdvisorUsername() {
        return advisorUsername;
    }

    public void setAdvisorUsername(String advisorUsername) {
        this.advisorUsername = advisorUsername;
    }

    public String getOutsideTeacherName() {
        return outsideTeacherName;
    }

    public void setOutsideTeacherName(String outsideTeacherName) {
        this.outsideTeacherName = outsideTeacherName;
    }

    public String getOutsideTeacherEmail() {
        return outsideTeacherEmail;
    }

    public void setOutsideTeacherEmail(String outsideTeacherEmail) {
        this.outsideTeacherEmail = outsideTeacherEmail;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }
    
    public String getJuryPresidentUsername() {
        return juryPresidentUsername;
    }

    public void setJuryPresidentUsername(String juryPresidentUsername) {
        this.juryPresidentUsername = juryPresidentUsername;
    }

    public String getJuryPresidentName() {
        return juryPresidentName;
    }

    public void setJuryPresidentName(String juryPresidentName) {
        this.juryPresidentName = juryPresidentName;
    }

    public String getAdvisorName() {
        return advisorName;
    }

    public void setAdvisorName(String advisorName) {
        this.advisorName = advisorName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTestDateString() {
        return testDateString;
    }

    public void setTestDateString(String testDateString) {
        this.testDateString = testDateString;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    
    
}
