package entities.users;

import entities.publictest.PublicTest;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="TEACHERS")
@NamedQueries({
    @NamedQuery(name = "getAllTeachers",
    query = "SELECT t FROM Teacher t ORDER BY t.name")})
public class Teacher extends Proponent implements Serializable {
    
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<PublicTest> publicTests;

    public Teacher() {
        publicTests = new LinkedList<>();
    }

    public Teacher(String username, String password, String name, String email) {
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
