package com.xitaymin;

public class CsvContainerNotAvailableException extends RuntimeException {
    public CsvContainerNotAvailableException() {
    }

    public CsvContainerNotAvailableException(String message) {
        super(message);
    }

    public CsvContainerNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
