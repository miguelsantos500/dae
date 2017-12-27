
package exceptions;


public class ApplicationNotPendingException extends Exception {

    public ApplicationNotPendingException(String msg) {
        super(msg);
    }
    public ApplicationNotPendingException() {
        super("A candidatura já não está pendente!");
    }
    

}
