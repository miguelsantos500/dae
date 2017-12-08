package entities.users;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CCP_USERS")
@NamedQueries({
    @NamedQuery(name = "getAllCCPUsers",
    query = "SELECT t FROM CCPUser t ORDER BY t.name")})
public class CCPUser extends User implements Serializable {

    public CCPUser() {

    }

    public CCPUser(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}
