package com.freelance.becker.japy.api.exception;

public class PythonMethodCallException extends Exception{

    public PythonMethodCallException(String message) {
        super(message);
    }

    public PythonMethodCallException(Throwable cause) {
        super(cause);
    }
}
