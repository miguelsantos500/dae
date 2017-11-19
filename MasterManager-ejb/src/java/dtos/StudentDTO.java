
package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Student")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentDTO extends UserDTO {

    private int courseCode;
    private String courseName;
    
    public StudentDTO(){
        
    }
    
    public StudentDTO(String username,
            String password,
            String name,
            String email,
            int courseCode,
            String courseName){
        super(username, password, name, email);
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    @Override
    public void reset() {
        super.reset(); 
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    
}
