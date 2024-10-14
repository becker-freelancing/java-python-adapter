package com.freelance.becker.japy.adapter;

import com.freelance.becker.japy.api.MethodReturnValue;
import com.freelance.becker.japy.api.PythonMethod;
import com.freelance.becker.japy.api.exception.PythonMethodCallException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PythonAdapterImplTest {

    PythonAdapterImpl pythonAdapter;
    PythonHttpClient pythonHttpClient;
    PythonMethod pythonMethod;


    @BeforeEach
    void setUp(){
        pythonHttpClient = mock(PythonHttpClient.class);
        pythonAdapter = new PythonAdapterImpl(pythonHttpClient);
        pythonMethod = mock(PythonMethod.class);
    }

    @Test
    void testCallMethodReturnsEmptyOptionalWhenPythonClientReturnsEmptyOptional() throws PythonMethodCallException {

        doReturn(Optional.empty()).when(pythonHttpClient).callMethod(pythonMethod);

        Optional<MethodReturnValue> methodReturnValue = pythonAdapter.callMethod(pythonMethod);

        assertTrue(methodReturnValue.isEmpty());
    }

    @Test
    void testCallMethodThrowsExceptionWhenPythonClientThrowsException() throws PythonMethodCallException {

        doThrow(PythonMethodCallException.class).when(pythonHttpClient).callMethod(pythonMethod);

        assertThrows(PythonMethodCallException.class, () -> pythonAdapter.callMethod(pythonMethod));
    }

    @Test
    void testCallMethodReturnsOptionalWhenPythonClientReturnsOptional() throws PythonMethodCallException {

        doReturn(Optional.of(mock(MethodReturnValue.class))).when(pythonHttpClient).callMethod(pythonMethod);

        Optional<MethodReturnValue> methodReturnValue = pythonAdapter.callMethod(pythonMethod);

        assertTrue(methodReturnValue.isPresent());
    }

}