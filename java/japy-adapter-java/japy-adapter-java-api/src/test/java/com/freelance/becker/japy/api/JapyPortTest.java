package com.freelance.becker.japy.api;

import com.freelance.becker.japy.api.exception.PythonMethodCallException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JapyPortTest {

    JapyPort japyPort;

    @BeforeEach
    void setup(){
        japyPort = JapyPort.getDefault();
    }


    @ParameterizedTest
    @MethodSource("parametersWithoutArguments")
    void testMethodWithoutReturnType(PythonMethod pythonMethod) throws PythonMethodCallException {

        Optional<MethodReturnValue> methodReturnValue =
                japyPort.callMethod(pythonMethod);

        assertTrue(methodReturnValue.isEmpty());
    }

    @ParameterizedTest
    @MethodSource("parametersWithArguments")
    void testMethodWithArguments(PythonMethod pythonMethod) throws PythonMethodCallException {

        Optional<MethodReturnValue> methodReturnValue =
                japyPort.callMethod(pythonMethod);

        assertFalse(methodReturnValue.isEmpty());
    }

    @Test
    void testNonExistingMethod(){

        assertThrows(PythonMethodCallException.class, () -> japyPort.callMethod(new PythonMethod("not_existing_method")));


    }

    static Stream<Arguments> parametersWithoutArguments() {
        return Stream.of(
                Arguments.of(new PythonMethod("void_method_with_one_arg", List.of(new PythonMethodArgument<>("ARG1")))),
                Arguments.of(new PythonMethod("void_method_with_two_args", List.of(new PythonMethodArgument<>("ARG1"), new PythonMethodArgument<>("ARG2")))),
                Arguments.of(new PythonMethod("void_method_with_no_args"))
        );
    }

    static Stream<Arguments> parametersWithArguments() {
        return Stream.of(
                Arguments.of(new PythonMethod("return_method_with_one_arg", List.of(new PythonMethodArgument<>("ARG1")))),
                Arguments.of(new PythonMethod("return_method_with_two_args", List.of(new PythonMethodArgument<>("ARG1"), new PythonMethodArgument<>("ARG2")))),
                Arguments.of(new PythonMethod("return_method_with_two_args_and_two_returns", List.of(new PythonMethodArgument<>("ARG1"), new PythonMethodArgument<>("ARG2")))),
                Arguments.of(new PythonMethod("return_method_with_no_args"))
        );
    }
  
}