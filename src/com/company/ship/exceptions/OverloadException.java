package com.company.ship.exceptions;

public class OverloadException  extends Exception {

    public OverloadException() {
        super();
    }

    public OverloadException(String message) {
        super(message);
    }

    public OverloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverloadException(Throwable cause) {
        super(cause);
    }
}
