package com.ubiquisoft.evaluation.exception;

public class CarCreatorException extends RuntimeException {

    public CarCreatorException(String message, Throwable e) {
        super(message, e);
    }

    public CarCreatorException(String message) {
        super(message);
    }
}
