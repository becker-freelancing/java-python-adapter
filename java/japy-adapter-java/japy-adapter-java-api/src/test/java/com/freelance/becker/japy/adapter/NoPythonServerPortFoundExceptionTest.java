package com.freelance.becker.japy.adapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoPythonServerPortFoundExceptionTest {

    @Test
    void testConstructor() {
        NoPythonServerPortFoundException exception = new NoPythonServerPortFoundException(1234, 6789);

        assertEquals("No Japy server found in port range (1234, 6789)", exception.getMessage());
    }

}