package web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;
import util.URILookup;

@ManagedBean
@SessionScoped
public class UploadManager1 {

    private List<UploadedFile> files;

    private UploadedFile file;

    private UploadedFile fileCv;
    private UploadedFile filePresentationLetter;
    private UploadedFile fileCertificate;

    private String completePathFile;
    private String filename;

    private List<String> filenames;
    private List<String> completePathFiles;

    public UploadManager1() {
        this.files = new ArrayList<>();
        this.filenames = new ArrayList<>();
        this.completePathFiles = new ArrayList<>();
    }

    public void upload() {

        if (file.getSize() != 0) {
            try {
                filename = file.getFileName().substring(file.getFileName().lastIndexOf("\\") + 1);

                completePathFile = URILookup.getServerDocumentsFolder() + filename;

                InputStream in = file.getInputstream();
                FileOutputStream out = new FileOutputStream(completePathFile);

                byte[] b = new byte[1024];
                int readBytes = in.read(b);

                while (readBytes != -1) {
                    out.write(b, 0, readBytes);
                    readBytes = in.read(b);
                }

                in.close();
                out.close();

                FacesMessage message = new FacesMessage("Ficheiro: " + file.getFileName() + " carregado com sucesso!");
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (IOException e) {
                FacesMessage message = new FacesMessage("ERRO :: Ficheiro: " + file.getFileName() + " não carregado!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("ERRO :: Ficheiro: "
                    + file.getFileName() + " não seleccionado ou vazio!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void uploadFiles() {
        uploadSpecificFile(this.fileCv);
        uploadSpecificFile(this.filePresentationLetter);
        uploadSpecificFile(this.fileCertificate);
    }

    public void uploadSpecificFile(UploadedFile specificFile) {

        if (specificFile.getSize() != 0) {
            try {
                filenames.add(specificFile.getFileName().substring(specificFile.getFileName().lastIndexOf("\\") + 1));

                completePathFiles.add(URILookup.getServerDocumentsFolder() + specificFile);

                InputStream in = specificFile.getInputstream();
                FileOutputStream out = new FileOutputStream(completePathFiles.get(completePathFiles.size() - 1));

                byte[] b = new byte[1024];
                int readBytes = in.read(b);

                while (readBytes != -1) {
                    out.write(b, 0, readBytes);
                    readBytes = in.read(b);
                }

                in.close();
                out.close();

                FacesMessage message = new FacesMessage("Ficheiro: " + specificFile.getFileName() + " carregado com sucesso!");
                FacesContext.getCurrentInstance().addMessage(null, message);

            } catch (IOException e) {
                FacesMessage message = new FacesMessage("ERRO :: Ficheiro: " + specificFile.getFileName() + " não carregado!");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } else {
            FacesMessage message = new FacesMessage("ERRO :: Ficheiro: "
                    + specificFile.getFileName() + " não seleccionado ou vazio!");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void resetFilesArray() {
        files.clear();
        completePathFiles.clear();
        filenames.clear();
    }

    public List<UploadedFile> getFiles() {
        List<UploadedFile> list = new ArrayList<>();
        list.add(fileCv);
        list.add(filePresentationLetter);
        list.add(fileCertificate);
        return list;
    }

    public void setFiles(List<UploadedFile> files) {
        this.files = files;
    }

    public UploadedFile getFile() {
        // this.file = this.files.get(position);

        return file;
    }

    public UploadedFile getFile(int index) {
        if (files.size() <= index) {
            return null;
        }
        return files.get(index);
    }

    public void addFile(UploadedFile file) {
        this.files.add(file);
    }

    public String getCompletePathFile() {
        return completePathFile;
    }

    public void setCompletePathFile(String completePathFile) {
        this.completePathFile = completePathFile;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public List<String> getCompletePathFiles() {
        return completePathFiles;
    }

    public UploadedFile getFileCv() {
        return fileCv;
    }

    public void setFileCv(UploadedFile fileCv) {
        this.fileCv = fileCv;
    }

    public UploadedFile getFilePresentationLetter() {
        return filePresentationLetter;
    }

    public void setFilePresentationLetter(UploadedFile filePresentationLetter) {
        this.filePresentationLetter = filePresentationLetter;
    }

    public UploadedFile getFileCertificate() {
        return fileCertificate;
    }

    public void setFileCertificate(UploadedFile fileCertificate) {
        this.fileCertificate = fileCertificate;
    }

}
