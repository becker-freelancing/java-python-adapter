package com.freelance.becker.japy.api;

import com.freelance.becker.japy.api.exception.PythonMethodCallException;

import java.util.Optional;

public interface JapyPort {

    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException;
}
