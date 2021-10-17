package com.xitaymin.exeptions;

public class BaseApplicationException extends RuntimeException {

    public BaseApplicationException(Throwable cause) {
        super(cause);
    }

    public BaseApplicationException(String message) {
        super(message);
    }

}
