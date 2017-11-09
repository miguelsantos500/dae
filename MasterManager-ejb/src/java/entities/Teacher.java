package entities;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Teacher extends User implements Serializable {

    public Teacher() {
    }

    public Teacher(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}
