package entities.users;

import entities.users.UserGroup.GROUP;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="INSTITUTIONS")
@NamedQueries({
    @NamedQuery(name = "getAllInstitutions",
    query = "SELECT i FROM Institution i ORDER BY i.name")})
public class Institution extends Proponent implements Serializable {

    public Institution() {
    }

    public Institution(String username, String password, String name, String email) {
        super(username, password, GROUP.Institution, name, email);
    }

}
