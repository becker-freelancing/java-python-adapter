package com.freelance.becker.japy.api.exception;

public class NotMappableException extends RuntimeException {

    public NotMappableException(Class<?> sourceClass) {
        super("Cannot apply mapper on class " + sourceClass.getName());
    }
}
