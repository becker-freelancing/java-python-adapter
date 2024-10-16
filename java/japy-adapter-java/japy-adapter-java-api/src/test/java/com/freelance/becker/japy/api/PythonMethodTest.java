package com.freelance.becker.japy.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PythonMethodTest {

    PythonMethod pythonMethodWithoutArguments;
    PythonMethod pythonMethodWithArguments;

    @BeforeEach
    void setUp() {
        pythonMethodWithoutArguments = new PythonMethod("without_arguments");
        pythonMethodWithArguments = new PythonMethod("with_arguments", List.of(new PythonMethodArgument<>("TEST"), new PythonMethodArgument<>(12)));
    }

    @Test
    void testGetName() {
        assertEquals("without_arguments", pythonMethodWithoutArguments.getName());
    }

    @Test
    void testGetArguments() {
        List<PythonMethodArgument<?>> arguments = pythonMethodWithArguments.getArguments();

        assertEquals(2, arguments.size());
    }

    @Test
    void testToString() {
        assertEquals("PythonMethod{name='without_arguments', arguments=[]}", pythonMethodWithoutArguments.toString());
    }
}