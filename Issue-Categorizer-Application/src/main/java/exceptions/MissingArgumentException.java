package exceptions;

/**
 * Exception for missing arguments when executing the tasks for running the application.
 *
 * @author xmokros 456442@mail.muni.cz
 */
public class MissingArgumentException extends Exception {
    public MissingArgumentException() {
    }

    public MissingArgumentException(String message) {
        super(message);
    }

    public MissingArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingArgumentException(Throwable cause) {
        super(cause);
    }

    public MissingArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
