/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import users.CCPUser;
import users.Teacher;

/**
 *
 * @author Armando
 */
public class Jury implements Serializable {

    private Teacher advisor;
    private Teacher teacher;
    private CCPUser ccpUser;

    public Jury(Teacher advisor, Teacher teacher, CCPUser ccpUser) {
        this.advisor = advisor;
        this.teacher = teacher;
        this.ccpUser = ccpUser;
    }

    public Teacher getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Teacher advisor) {
        this.advisor = advisor;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public CCPUser getCcpUser() {
        return ccpUser;
    }

    public void setCcpUser(CCPUser ccpUser) {
        this.ccpUser = ccpUser;
    }

}
