package com.freelance.becker.japy.adapter;

import com.freelance.becker.japy.api.MethodReturnValue;
import com.freelance.becker.japy.api.PythonMethod;
import com.freelance.becker.japy.api.exception.PythonMethodCallException;

import java.util.Optional;

public class PythonAdapterImpl implements PythonAdapter{

    private PythonHttpClient pythonClient;

    protected PythonAdapterImpl(PythonHttpClient pythonClient) {
        this.pythonClient = pythonClient;
    }

    public PythonAdapterImpl(){
        //TODO: Dynamic Port
        this(new PythonHttpClient("127.0.0.1", 12345));
    }

    @Override
    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException {
        return pythonClient.callMethod(pythonMethod);
    }
}
