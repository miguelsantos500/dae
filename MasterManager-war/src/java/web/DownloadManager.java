package web;

import dtos.DocumentDTO;
import ejbs.PublicTestBean;
import exceptions.EntityDoesNotExistException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
public class DownloadManager implements Serializable {

    private static final Logger logger = Logger.getLogger("web.downloadManager");
    
    private int code;

    @EJB
    private PublicTestBean publicTestBean;
    
    public DownloadManager() {
        
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
   
    public StreamedContent getFile() {
        try {
            UIComponent component = UIComponent.getCurrentComponent(FacesContext.getCurrentInstance());
            UIParameter param = (UIParameter) component.findComponent("codeParam");
            code = Integer.parseInt(param.getValue().toString());
            
            DocumentDTO document = publicTestBean.getDocument(code);
            InputStream in = new FileInputStream(document.getFilepath());

            return new DefaultStreamedContent(in, document.getMimeType(), document.getDesiredName());
        } catch (EntityDoesNotExistException | FileNotFoundException  e) {
            FacesExceptionHandler.handleException(e, "Could not download the file", logger);
            return null;
        }
    }    
}
