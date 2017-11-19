/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.InputStream;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Miguel
 */
@ManagedBean
public class DownloadManager {

    private static final Logger logger = Logger.getLogger("web.downloadManager");
    private StreamedContent file;

    public DownloadManager() {
        try {
            InputStream stream = FacesContext.getCurrentInstance().getExternalContext()
                    .getResourceAsStream("/resources/files/IS_WORKSHEET_06_MESSAGING.pdf");
            file = new DefaultStreamedContent(stream, "application/pdf", "EI_DAE_2017_2018_FOLHA4.pdf");
        } catch (Exception e) {
            FacesExceptionHandler.handleException(e, "Can't download the file!", logger);
        }
    }

    public StreamedContent getFile() {
        return file;
    }
}