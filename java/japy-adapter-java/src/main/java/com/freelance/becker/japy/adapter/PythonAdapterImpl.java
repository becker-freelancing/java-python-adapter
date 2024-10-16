package com.freelance.becker.japy.adapter;

import com.freelance.becker.japy.api.MethodReturnValue;
import com.freelance.becker.japy.api.PythonMethod;
import com.freelance.becker.japy.api.exception.PythonMethodCallException;

import java.util.Optional;

class PythonAdapterImpl implements PythonAdapter {

    private static final String HOST = "localhost";

    private PythonHttpClient pythonClient;

    protected PythonAdapterImpl(PythonHttpClient pythonClient) {
        this.pythonClient = pythonClient;
    }

    public PythonAdapterImpl(){
        this(new PythonHttpClient(HOST, new DynamicPortFinder(HOST).findPort()));
    }

    @Override
    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException {
        return pythonClient.callMethod(pythonMethod);
    }
}
