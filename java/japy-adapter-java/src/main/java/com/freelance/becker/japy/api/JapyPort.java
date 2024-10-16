package com.freelance.becker.japy.api;

import com.freelance.becker.japy.api.exception.PythonMethodCallException;

import java.util.Optional;

public interface JapyPort {

    public static JapyPort getDefault(){
        return new JapyPortImpl();
    }

    /**
     * Calls a method in Python.
     *
     * @param pythonMethod
     * @return
     * @throws PythonMethodCallException
     * @see <a href="https://github.com/becker-freelancing/java-python-adapter/blob/main/README.md">README.md</a>
     */
    public Optional<MethodReturnValue> callMethod(PythonMethod pythonMethod) throws PythonMethodCallException;
}
