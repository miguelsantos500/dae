
package dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Student")
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentDTO extends UserDTO {

    
    public StudentDTO(){
        
    }
    
    public StudentDTO(String username,
            String password,
            String name,
            String email){
        super(username, password, name, email);
        
    }

    @Override
    public void reset() {
        super.reset(); 
    }
    
    
    
    
    
}
