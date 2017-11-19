
package web;

import ejbs.users.CCPUserBean;
import ejbs.users.InstitutionBean;
import ejbs.users.StudentBean;
import ejbs.users.TeacherBean;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class AdminstratorManager {
    
    @EJB
    private CCPUserBean ccpUserBean;
     @EJB
    private InstitutionBean institutionBean;
      @EJB
    private StudentBean studentBean;
       @EJB
    private TeacherBean teacherBean;
       
    
    

}
