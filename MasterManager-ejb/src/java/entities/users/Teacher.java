package entities.users;

import entities.users.User;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="TEACHERS")
@NamedQueries({
    @NamedQuery(name = "getAllTeachers",
    query = "SELECT t FROM Teacher t ORDER BY t.name")})
public class Teacher extends Proponent implements Serializable {

    public Teacher() {
    }

    public Teacher(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}
