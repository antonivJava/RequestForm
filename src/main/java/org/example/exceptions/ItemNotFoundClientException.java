package org.example.exceptions;

public class ItemNotFoundClientException extends RuntimeException {

    public ItemNotFoundClientException() {
        super();
    }

    public ItemNotFoundClientException(String message) {
        super(message);
    }

    public ItemNotFoundClientException(Throwable throwable) {
        super(throwable);
    }

    public ItemNotFoundClientException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
