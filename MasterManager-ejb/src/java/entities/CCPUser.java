package entities;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class CCPUser extends User implements Serializable {

    public CCPUser() {
    }

    public CCPUser(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}
