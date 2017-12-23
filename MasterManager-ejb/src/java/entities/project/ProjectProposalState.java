/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.project;

import java.io.Serializable;

/**
 *
 * @author Miguel
 */
public enum ProjectProposalState implements Serializable {
    PENDING, ACCEPTED, NOT_ACCEPTED, ASSIGNED, FINISHED;
}
