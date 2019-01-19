package by.training.project.exception;


public final class ExceptionMessage {
    public static final String CONNECTION_POOL_EXCEPTION_MESSAGE = "Connection pool error";
    public static final String DAO_EXCEPTION_MESSAGE = "Database error";
    public static final String TRANSACTION_EXCEPTION_MESSAGE = "Transaction error";
    public static final String SERVICE_EXCEPTION_MESSAGE = "Dao layer error";
    public static final String COMMAND_EXCEPTION_MESSAGE = "Service layer error";

    private ExceptionMessage() {
    }
}
