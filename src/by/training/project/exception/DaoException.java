package by.training.project.exception;

public class DaoException extends Exception {
    /**
     * Constructs a new exception with the specified detail message.
     *
     * @param message the detail message.
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized
     */
    public DaoException() {
        super();
    }

    /**
     * Constructs a new exception with cause.
     *
     * @param cause the cause
     */
    public DaoException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new exception with the specified detail message and
     * cause.
     *
     * @param message is a message the detail message
     * @param cause   the cause
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
