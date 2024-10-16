package com.freelance.becker.japy.api.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


class NotMappableExceptionTest {

    @Test
    void testMessageIsBuildCorrectly() {
        NotMappableException notMappableException = new NotMappableException(Object.class);

        assertEquals("Cannot apply mapper on class java.lang.Object", notMappableException.getMessage());
    }
}