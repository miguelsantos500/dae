/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Migue
 */
@ManagedBean
public class UploadManager {

    private UploadedFile file;

    private static final String FILE_PATH = 
            "C:\\Users\\Migue\\Documents\\NetBeansProjects\\AcademicManagement_Aula\\AcademicManagement_Aula-war\\web\\resources\\files\\";
    
    /**
     * Creates a new instance of UploadManager
     */
    public UploadManager() {
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            try {
                String filename
                        = file.getFileName().substring(file.getFileName()
                                .lastIndexOf("\\") + 1);
                
                InputStream in = file.getInputstream();
                FileOutputStream out = new FileOutputStream(
                        FILE_PATH + filename);

                byte[] b = new byte[1024];
                int readBytes = in.read(b);

                while (readBytes != -1) {
                    out.write(b, 0, readBytes);
                    readBytes = in.read(b);
                }

                in.close();
                out.close();
                FacesMessage message = new FacesMessage("File: " + 
                        file.getFileName() + "uploaded successfully!");
                
                FacesContext.getCurrentInstance().addMessage(null, message);
            } catch (IOException e) {

                FacesMessage message = new FacesMessage("ERROR :: File: "
                        + file.getFileName() + " not uploaded!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        }
    }

}
