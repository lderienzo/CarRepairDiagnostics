package com.ubiquisoft.evaluation.exception;

public final class CarCreatorException extends RuntimeException {

    public CarCreatorException(String message, Throwable e) {
        super(message, e);
    }

    public CarCreatorException(Throwable e) {
        super(e);
    }

    public CarCreatorException(String message) {
        super(message);
    }
}
