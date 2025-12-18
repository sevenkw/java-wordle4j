package wordleexception;

public class DictionaryFileNotFoundException extends RuntimeException {
    public DictionaryFileNotFoundException(String message) {
        super(message);
    }
}
