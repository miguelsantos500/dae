
package exceptions;


public class ApplicationNumberException extends Exception {

    public ApplicationNumberException(String msg) {
        super(msg);
    }
    public ApplicationNumberException() {
        super("O máximo de candidaturas permitidas é cinco!");
    }
    

}
