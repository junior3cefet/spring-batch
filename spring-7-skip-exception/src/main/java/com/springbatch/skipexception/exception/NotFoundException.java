package com.springbatch.skipexception.exception;

public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The customer with name %s was not found!";

    public NotFoundException(String nome) {
        super(String.format(DEFAULT_MESSAGE, nome));
    }
}
