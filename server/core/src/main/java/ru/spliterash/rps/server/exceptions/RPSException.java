package ru.spliterash.rps.server.exceptions;

public abstract class RPSException extends RuntimeException {
    public RPSException() {
    }

    public RPSException(String message) {
        super(message);
    }

    public RPSException(String message, Throwable cause) {
        super(message, cause);
    }

    public RPSException(Throwable cause) {
        super(cause);
    }

    public RPSException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
