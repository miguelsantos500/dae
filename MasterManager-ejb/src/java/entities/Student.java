package entities;

import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Student extends User implements Serializable {

    public Student() {
    }

    public Student(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

}
