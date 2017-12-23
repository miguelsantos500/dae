
package exceptions;


public class StudentAlreadyAppliedException extends Exception {

    public StudentAlreadyAppliedException(String msg) {
        super(msg);
    }
    public StudentAlreadyAppliedException() {
        super("This student is already applied to that project proposal.");
    }
    

}
