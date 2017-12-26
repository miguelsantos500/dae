
package exceptions;


public class ProjectProposalStateNotChangedException extends Exception {

    public ProjectProposalStateNotChangedException(String msg) {
        super(msg);
    }
    public ProjectProposalStateNotChangedException() {
        super("There is no change in the Project Proposal State!");
    }
    

}
