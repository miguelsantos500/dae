
package entities;

import java.io.Serializable;


public enum ApplicationState implements Serializable {
    PENDING, ACCEPTED, NOT_ACCEPTED, ASSIGNED;
}
