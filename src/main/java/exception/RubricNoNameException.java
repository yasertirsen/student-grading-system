package exception;

public class RubricNoNameException extends Exception{
    public RubricNoNameException() {
        super();
    }

    public RubricNoNameException(String message) {
        super(message);
    }
}
