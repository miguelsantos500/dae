
package entities.users;

import entities.users.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="PROPONENTS")
@NamedQueries({
    @NamedQuery(name = "getAllProponents",
    query = "SELECT p FROM Proponent p ORDER BY p.name")})
public class Proponent extends User implements Serializable {

    public Proponent() {
    }

    public Proponent(String username, String password,GROUP group, String name, String email) {
        super(username, password, group, name, email);
    }

    
}
