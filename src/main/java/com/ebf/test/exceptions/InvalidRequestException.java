package com.ebf.test.exceptions;

public class InvalidRequestException extends AbstractException {

    private static final long serialVersionUID = -3268755620724061209L;

    public InvalidRequestException(String message) {
        super(message);
    }

    public InvalidRequestException(String message, String... args) {
        super(message, args);
    }

}
