package exceptions;

/**
 * Exception for missing arguments when executing the tasks for running the application.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class MissingTaskArgumentException extends Exception {
    public MissingTaskArgumentException() {
    }

    public MissingTaskArgumentException(String message) {
        super(message);
    }

    public MissingTaskArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingTaskArgumentException(Throwable cause) {
        super(cause);
    }

    public MissingTaskArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
