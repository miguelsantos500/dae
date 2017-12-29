package entities;

import entities.publictest.PublicTest;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DOCUMENTS")
@NamedQueries({
    @NamedQuery(name = "getDocumentOfPublicTest", query = "SELECT doc FROM Document doc WHERE doc.publicTest.code = :code")
})
public class Document implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String filepath;

    private String desiredName;

    private String mimeType;

    @OneToOne(mappedBy = "fileRecord", cascade = CascadeType.REMOVE)
    private PublicTest publicTest;
    
    @OneToOne(mappedBy = "fileRecord", cascade = CascadeType.REMOVE)
    private Application application;

    public Document() {

    }

    public Document(String filepath, String desiredName, String mimeType, EntitieGeneric<?> entity) {
        this.filepath = filepath;
        this.desiredName = desiredName;
        this.mimeType = mimeType;
        
        if (entity.getEntity().getClass().equals(PublicTest.class)) {
            this.publicTest = (PublicTest) entity.getEntity();
        }else if(entity.getEntity().getClass().equals(Application.class)){
            this.application = (Application) entity.getEntity();
        }
        
        

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getDesiredName() {
        return desiredName;
    }

    public void setDesiredName(String desiredName) {
        this.desiredName = desiredName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public PublicTest getStudent() {
        return publicTest;
    }

    public void setStudent(PublicTest publicTest) {
        this.publicTest = publicTest;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
    
    
}
