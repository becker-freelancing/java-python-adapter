package com.freelance.becker.japy.api.exception;

public class PythonMethodCallException extends Exception{

    public PythonMethodCallException(Throwable cause) {
        super("Exception on calling Python Method", cause);
    }
}
