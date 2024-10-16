package com.freelance.becker.japy.api.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PythonMethodCallExceptionTest {

    @Test
    void testMessageConstructorSetsAttributeCorrectly(){
        PythonMethodCallException exception = new PythonMethodCallException("I am a message");

        assertEquals("I am a message", exception.getMessage());
    }

    @Test
    void testThrowableConstructorSetsAttributeCorrectly(){
        Exception cause = new Exception("I am the cause");
        PythonMethodCallException exception = new PythonMethodCallException(cause);

        assertEquals(cause, exception.getCause());
    }

}