package by.training.project.exception;

public class ConnectionPoolException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public ConnectionPoolException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message is a message the detail message
     * @param cause   the cause
     */
    public ConnectionPoolException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new connection pool exception with {@code null} as its detail message.
     * The cause is not initialized
     */
    public ConnectionPoolException() {
        super();
    }

    /**
     * Constructs a new exception with cause.
     *
     * @param cause the cause
     */
    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
