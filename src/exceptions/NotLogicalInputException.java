package exceptions;

public class NotLogicalInputException extends Exception {
    public NotLogicalInputException(String msg){
        super(msg);
        System.out.println("Not a logial input: "+msg);
    }
}
