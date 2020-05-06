package exceptions;

public class ImproperInputException extends Exception {
    public ImproperInputException(String msg){
        super(msg);
        System.out.println("Improper input: "+msg);
    }
}
