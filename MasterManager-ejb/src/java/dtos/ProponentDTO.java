
package dtos;

import java.io.Serializable;


public class ProponentDTO extends UserDTO implements Serializable {

    public ProponentDTO() {
    }

    public ProponentDTO(String username, String password, String name, String email) {
        super(username, password, name, email);
    }
    
    

}
