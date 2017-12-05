package entities.users;

import entities.publictest.PublicTest;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CCP_USERS")
public class CCPUser extends User implements Serializable {
    
    @OneToMany(mappedBy = "juryPresident", cascade = CascadeType.REMOVE)
    private List<PublicTest> publicTests;
    
    public CCPUser() {
        publicTests = new LinkedList<>();
    }

    public CCPUser(String username, String password, String name, String email) {
        super(username, password, name, email);
        publicTests = new LinkedList<>();
    }

    public List<PublicTest> getPublicTests() {
        return publicTests;
    }

    public void setPublicTests(List<PublicTest> publicTests) {
        this.publicTests = publicTests;
    }
    
    public void addPublicTest(PublicTest publicTest) {
        this.publicTests.add(publicTest);
    }
    
    public void removePublicTest(PublicTest publicTest) {
        this.publicTests.remove(publicTest);
    }
}
