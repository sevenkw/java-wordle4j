package Wordleexception;

public class WordNotFoundInDictionaryException extends Exception {
    public WordNotFoundInDictionaryException(String message) {
        super(message);
    }
}
