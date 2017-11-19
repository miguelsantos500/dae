package users;

import users.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TEACHERS")
public class Teacher extends User implements Serializable {

    public Teacher() {
    }

    public Teacher(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}