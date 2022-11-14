package com.ebf.test.exceptions;

public abstract class AbstractException extends RuntimeException {

    private static final long serialVersionUID = 4444203114045407970L;
    private String[] args;

    public AbstractException(String message) {
        super(message);
    }

    public AbstractException(String message, Exception e) {
        super(message, e);
    }

    public AbstractException(String message, String... args) {
        super(message);
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
