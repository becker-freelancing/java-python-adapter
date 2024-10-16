package com.freelance.becker.japy.adapter;

import com.freelance.becker.japy.api.MethodReturnValue;
import com.freelance.becker.japy.api.PythonMethod;
import com.freelance.becker.japy.api.exception.PythonMethodCallException;

import java.util.Optional;

public interface PythonAdapter {

    public static PythonAdapter getDefault(){
        return new PythonAdapterImpl();
    }

    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException;
}
