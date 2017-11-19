package entities.users;

import entities.users.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="CCP_USERS")
public class CCPUser extends User implements Serializable {

    public CCPUser() {
    }

    public CCPUser(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}
