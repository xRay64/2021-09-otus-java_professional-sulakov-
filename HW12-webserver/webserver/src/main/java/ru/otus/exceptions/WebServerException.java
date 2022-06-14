package ru.otus.exceptions;

public class WebServerException extends RuntimeException {
    public WebServerException() {
        super();
    }

    public WebServerException(String message) {
        super(message);
    }

    public WebServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebServerException(Throwable cause) {
        super(cause);
    }
}
