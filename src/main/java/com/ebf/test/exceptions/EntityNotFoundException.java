package com.ebf.test.exceptions;

public class EntityNotFoundException extends AbstractException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, String... args) {
        super(message, args);
    }

}
