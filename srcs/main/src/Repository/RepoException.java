package Repository;

public class RepoException extends RuntimeException {
    public RepoException(String message) {
        super(message);
    }
    public RepoException(String message, Throwable cause) {
        super(message, cause);
    }
}
