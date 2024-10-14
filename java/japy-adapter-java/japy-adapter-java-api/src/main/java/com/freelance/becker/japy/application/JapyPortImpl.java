package com.freelance.becker.japy.application;

import com.freelance.becker.japy.adapter.PythonAdapter;
import com.freelance.becker.japy.api.JapyPort;
import com.freelance.becker.japy.api.MethodReturnValue;
import com.freelance.becker.japy.api.PythonMethod;
import com.freelance.becker.japy.api.exception.PythonMethodCallException;

import java.util.Optional;

public class JapyPortImpl implements JapyPort {

    private PythonAdapter pythonAdapter;

    public JapyPortImpl(){
        pythonAdapter = PythonAdapter.getDefault();
    }

    @Override
    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException {
        return pythonAdapter.callMethod(pythonMethod);
    }
}
