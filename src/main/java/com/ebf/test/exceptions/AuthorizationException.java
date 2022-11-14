package com.ebf.test.exceptions;

public class AuthorizationException extends AbstractException {

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, String... args) {
        super(message, args);
    }

}
