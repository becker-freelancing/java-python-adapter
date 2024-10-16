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

    static Stream<Arguments> parametersForComplexBaseClass() {
        return Stream.of(
                Arguments.of(new PythonMethod("create_complex_base_class"), new ComplexBaseClass("Arg1", "Arg2", "func"))
        );
    }

    static Stream<Arguments> parametersForComplexSubClass() {
        return Stream.of(
                Arguments.of(new PythonMethod("create_complex_sub_class"), new ComplexSubClass("Arg1", "Arg2", "TEST", "abc")),
                Arguments.of(new PythonMethod("create_complex_sub_class_with_param"), new ComplexSubClass("Arg1", "Arg2", "TEST", "abc2")),
                Arguments.of(new PythonMethod("create_complex_sub_class_with_param", List.of(new PythonMethodArgument<>("JavaArg"))), new ComplexSubClass("Arg1", "Arg2", "TEST", "JavaArg")),
                Arguments.of(new PythonMethod("create_complex_sub_class_with_inner_class"), new ComplexSubClass("Arg1", "Arg2", "TEST", "abc3", new ComplexBaseClass("Arg1", "Arg2", "func")))
        );
    }

    static Stream<Arguments> parametersForTupleWithComplexClasses() {
        return Stream.of(
                Arguments.of(new PythonMethod("create_complex_classes"), List.of(new ComplexBaseClass("Arg1", "Arg2", "func"), new ComplexSubClass("Arg1", "Arg2", "TEST", "abc")))
        );
    }

    static Stream<Arguments> parametersWithArguments() {
        return Stream.of(
                Arguments.of(new PythonMethod("return_method_with_one_arg", List.of(new PythonMethodArgument<>("ARG1"))), "ARG1"),
                Arguments.of(new PythonMethod("return_method_with_two_args", List.of(new PythonMethodArgument<>("ARG1"), new PythonMethodArgument<>("ARG2"))), "ARG1"),
                Arguments.of(new PythonMethod("return_method_with_two_args_and_two_returns", List.of(new PythonMethodArgument<>("ARG1"), new PythonMethodArgument<>("ARG2"))), List.of("ARG1", "ARG2")),
                Arguments.of(new PythonMethod("return_method_with_no_args"), "TEST")
        );
    }

    @Test
    void testNonExistingMethod(){

        assertThrows(PythonMethodCallException.class, () -> japyPort.callMethod(new PythonMethod("not_existing_method")));


    }

    @ParameterizedTest
    @MethodSource("parametersWithArguments")
    void testMethodWithArguments(PythonMethod pythonMethod, Object expectedReturnValue) throws PythonMethodCallException {

        Optional<MethodReturnValue> methodReturnValue =
                japyPort.callMethod(pythonMethod);

        assertFalse(methodReturnValue.isEmpty());
        assertEquals(expectedReturnValue, methodReturnValue.get().getReturnValue());
    }

    @ParameterizedTest
    @MethodSource("parametersForComplexBaseClass")
    void testComplexBaseClass(PythonMethod pythonMethod, ComplexBaseClass expected) throws PythonMethodCallException {
        Optional<MethodReturnValue> methodReturnValue = japyPort.callMethod(pythonMethod);

        assertTrue(methodReturnValue.isPresent());
        assertEquals(expected, methodReturnValue.get().castExactly(ComplexBaseClass.class));
    }

    @ParameterizedTest
    @MethodSource("parametersForComplexSubClass")
    void testComplexSubClass(PythonMethod pythonMethod, ComplexSubClass expected) throws PythonMethodCallException {
        Optional<MethodReturnValue> methodReturnValue = japyPort.callMethod(pythonMethod);

        assertTrue(methodReturnValue.isPresent());
        assertEquals(expected, methodReturnValue.get().castExactly(ComplexSubClass.class));
    }


    static Stream<Arguments> parametersWithoutArguments() {
        return Stream.of(
                Arguments.of(new PythonMethod("void_method_with_one_arg", List.of(new PythonMethodArgument<>("ARG1")))),
                Arguments.of(new PythonMethod("void_method_with_two_args", List.of(new PythonMethodArgument<>("ARG1"), new PythonMethodArgument<>("ARG2")))),
                Arguments.of(new PythonMethod("void_method_with_no_args"))
        );
    }

    @ParameterizedTest
    @MethodSource("parametersForTupleWithComplexClasses")
    void testTupleWithComplexClasses(PythonMethod pythonMethod, List<ComplexBaseClass> expected) throws PythonMethodCallException {
        Optional<MethodReturnValue> methodReturnValue = japyPort.callMethod(pythonMethod);

        assertTrue(methodReturnValue.isPresent());
        assertEquals(expected, methodReturnValue.get().castListWithDifferentClassesExactly(ComplexBaseClass.class, ComplexSubClass.class));
    }
}