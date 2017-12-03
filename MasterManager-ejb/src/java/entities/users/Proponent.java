
package entities.users;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="PREPONENTS")
public class Proponent extends User implements Serializable {

    public Proponent() {
    }

    public Proponent(String username, String password, String name, String email) {
        super(username, password, name, email);
    }

    
}
