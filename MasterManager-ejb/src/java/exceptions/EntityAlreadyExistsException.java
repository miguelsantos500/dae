
package exceptions;


public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }
    public EntityAlreadyExistsException() {
        super("Entity Already Exists!");
    }
    

}
