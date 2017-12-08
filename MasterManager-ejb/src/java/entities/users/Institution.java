package entities.users;

import entities.users.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="INSTITUTIONS")
public class Institution extends Proponent implements Serializable {

    public Institution() {
    }

    public Institution(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}
