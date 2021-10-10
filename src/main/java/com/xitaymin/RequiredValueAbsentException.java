package com.xitaymin;

public class RequiredValueAbsentException extends RuntimeException {
    public RequiredValueAbsentException() {
    }

    public RequiredValueAbsentException(String message) {
        super(message);
    }

    public RequiredValueAbsentException(String message, Throwable cause) {
        super(message, cause);
    }
}
