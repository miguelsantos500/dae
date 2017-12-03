/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Armando
 */
public class PublicTestDTO implements Serializable {

    private int code;
    private String title;
    private Date testDateTime;
    private String Place;
    private String link;
    private String ccpUserUsername;
    private String teacherUsername;
    private String outsideTeacherName;
    private String outsideTeacherEmail;
    private String studentUsername;
    private File fileRecord;

    public PublicTestDTO() {
    }

    public PublicTestDTO(int code, String title, Date testDateTime,
            String Place, String link, String ccpUserUsername, String teacherUsername, 
            String outsideTeacherName, String outsideTeacherEmail, String studentUsername, 
            File fileRecord) {
        this.code = code;
        this.title = title;
        this.testDateTime = testDateTime;
        this.Place = Place;
        this.link = link;
        this.ccpUserUsername = ccpUserUsername;
        this.teacherUsername = teacherUsername;
        this.outsideTeacherName = outsideTeacherName;
        this.outsideTeacherEmail = outsideTeacherEmail;
        this.studentUsername = studentUsername;
        this.fileRecord = fileRecord;
    }
    
    public void reset() {
        this.code = 0;
        this.title = null;
        this.testDateTime = null;
        this.Place = null;
        this.link = null;
        this.ccpUserUsername = null;
        this.teacherUsername = null;
        this.outsideTeacherName = null;
        this.outsideTeacherEmail = null;
        this.studentUsername = null;
        this.fileRecord = null;
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

    public String getCCPUserUsername() {
        return ccpUserUsername;
    }

    public void setCCPUserUsername(String ccpUserUsername) {
        this.ccpUserUsername = ccpUserUsername;
    }

    public String getTeacherUsername() {
        return teacherUsername;
    }

    public void setTeacherUsername(String teacherUsername) {
        this.teacherUsername = teacherUsername;
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

    public File getFileRecord() {
        return fileRecord;
    }

    public void setFileRecord(File fileRecord) {
        this.fileRecord = fileRecord;
    }
    
    
    
}
