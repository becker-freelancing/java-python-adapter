package com.freelance.becker.japy.api;

import com.freelance.becker.japy.adapter.PythonAdapter;
import com.freelance.becker.japy.api.exception.PythonMethodCallException;

import java.util.Optional;

class JapyPortImpl implements JapyPort {

    private PythonAdapter pythonAdapter;

    public JapyPortImpl(){
        pythonAdapter = PythonAdapter.getDefault();
    }

    @Override
    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException {
        return pythonAdapter.callMethod(pythonMethod);
    }
}
