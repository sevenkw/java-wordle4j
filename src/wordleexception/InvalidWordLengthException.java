package wordleexception;

public class InvalidWordLengthException extends RuntimeException {
    public InvalidWordLengthException(String message) {
        super(message);
    }
}
