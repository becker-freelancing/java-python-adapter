package com.freelance.becker.japy.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PythonMethodArgumentTest {

    PythonMethodArgument<String> argument;

    @BeforeEach
    void setUp() {
        argument = new PythonMethodArgument<>("TEST");
    }

    @Test
    void testGetArgument() {
        assertEquals("TEST", argument.getArgument());
    }

    @Test
    void testToString() {
        assertEquals("PythonMethodArgument{argument=TEST}", argument.toString());
    }
}