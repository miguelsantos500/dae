
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Institution")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstitutionDTO extends ProponentDTO implements Serializable {

    public InstitutionDTO() {
    }

    public InstitutionDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    @Override
    public void reset() {
        super.reset(); 
    }

    
    
}
