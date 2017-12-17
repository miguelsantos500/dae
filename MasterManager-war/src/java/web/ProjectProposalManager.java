/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import dtos.ProjectProposalDTO;
import entities.project.ProjectType;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Miguel
 */
@Named("projectProposalManager")
@RequestScoped
public class ProjectProposalManager {

    private ProjectProposalDTO currentProjectProposal;
    private ProjectProposalDTO newProjectProposal;

    private HtmlPanelGrid mainGrid;

    private static final Logger logger = Logger.getLogger("web.ProjectProposalManager");

    /**
     * Creates a new instance of ProjectProposalManager
     */
    public ProjectProposalManager() {
        this.newProjectProposal = new ProjectProposalDTO();
    }

    public void addInputText() {
        logger.info("Hello!");
        mainGrid = (HtmlPanelGrid) FacesContext.getCurrentInstance().getApplication()
                .createComponent(HtmlPanelGrid.COMPONENT_TYPE);

        final HtmlOutputLabel label = (HtmlOutputLabel) FacesContext.getCurrentInstance().getApplication()
                .createComponent(HtmlOutputLabel.COMPONENT_TYPE);

        mainGrid.getChildren().add(label);

        final HtmlInputText htmlInputText = (HtmlInputText) FacesContext.getCurrentInstance().getApplication()
                .createComponent(HtmlInputText.COMPONENT_TYPE);

        mainGrid.getChildren().add(htmlInputText);
        
        RequestContext.getCurrentInstance().update(mainGrid.getId());
        
    }

    public ProjectType[] getProjectTypes() {
        return ProjectType.values();
    }

    public ProjectProposalDTO getCurrentProjectProposal() {
        return currentProjectProposal;
    }

    public void setCurrentProjectProposal(ProjectProposalDTO currentProjectProposal) {
        this.currentProjectProposal = currentProjectProposal;
    }

    public ProjectProposalDTO getNewProjectProposal() {
        return newProjectProposal;
    }

    public void setNewProjectProposal(ProjectProposalDTO newProjectProposal) {
        this.newProjectProposal = newProjectProposal;
    }

    public HtmlPanelGrid getMainGrid() {
        if (null == mainGrid) {
            mainGrid = (HtmlPanelGrid) FacesContext.getCurrentInstance().getApplication()
                    .createComponent(HtmlPanelGrid.COMPONENT_TYPE);
            mainGrid.setId("mainGrid");
        }
        return mainGrid;
    }

    public void setMainGrid(HtmlPanelGrid mainGrid) {
        this.mainGrid = mainGrid;
    }

}
