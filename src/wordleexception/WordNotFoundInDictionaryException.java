package wordleexception;

public class WordNotFoundInDictionaryException extends RuntimeException {
    public WordNotFoundInDictionaryException(String message) {
        super(message);
    }
}
