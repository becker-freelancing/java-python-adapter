package com.freelance.becker.japy.api;

import com.freelance.becker.japy.api.exception.PythonMethodCallException;
import com.freelance.becker.japy.application.JapyPortImpl;

import java.util.Optional;

public interface JapyPort {

    public static JapyPort getDefault(){
        return new JapyPortImpl();
    }

    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException;
}
