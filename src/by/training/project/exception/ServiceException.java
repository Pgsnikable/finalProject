package by.training.project.exception;

public class ServiceException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message is a message the detail message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructs a new exception with cause.
     *
     * @param cause the cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
