package entities;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Institution extends User implements Serializable {

    public Institution() {
    }

    public Institution(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}
