
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Proponent")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProponentDTO extends UserDTO implements Serializable {

    public ProponentDTO() {
    }

    public ProponentDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
    
    

}
