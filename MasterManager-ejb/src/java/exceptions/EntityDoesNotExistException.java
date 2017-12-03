
package exceptions;


public class EntityDoesNotExistException extends Exception {

    public EntityDoesNotExistException(String msg) {
        super(msg);
    }
    public EntityDoesNotExistException() {
        super("This Entity Does not Exist!");
    }
    

}
