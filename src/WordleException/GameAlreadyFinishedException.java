package WordleException;

public class GameAlreadyFinishedException extends Exception{
    public GameAlreadyFinishedException(String message) {
        super(message);
    }
}
