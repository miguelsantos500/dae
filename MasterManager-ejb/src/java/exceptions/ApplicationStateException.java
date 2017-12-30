
package exceptions;


public class ApplicationStateException extends Exception {

    public ApplicationStateException(String msg) {
        super(msg);
    }
    public ApplicationStateException() {
        super("Estado inválido!");
    }
    

}
