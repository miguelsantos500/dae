
package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class CCPUser extends User implements Serializable {

    public CCPUser() {
    }

    public CCPUser(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    
   
  
}
