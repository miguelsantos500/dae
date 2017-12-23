
package exceptions;


public class StudentNotAppliedException extends Exception {

    public StudentNotAppliedException(String msg) {
        super(msg);
    }
    public StudentNotAppliedException() {
        super("There is no student applied to that project proposal with that username!");
    }
    

}
