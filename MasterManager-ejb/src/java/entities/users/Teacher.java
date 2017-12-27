package entities.users;

import entities.project.Project;
import entities.publictest.PublicTest;
import entities.users.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    public Teacher() {
        publicTests = new LinkedList<>();
    }

    public Teacher(String username, String password, String name, String email) {
        super(username, password, GROUP.Teacher, name, email);
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    
    
    
}
