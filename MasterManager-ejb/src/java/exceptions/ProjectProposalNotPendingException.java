
package exceptions;


public class ProjectProposalNotPendingException extends Exception {

    public ProjectProposalNotPendingException(String msg) {
        super(msg);
    }
    public ProjectProposalNotPendingException() {
        super("Project Propsal not in state PENDING.");
    }
    

}
