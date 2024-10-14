package com.freelance.becker.japy.api;

import java.util.List;

public class PythonMethod {


    private String name;
    private List<PythonMethodArgument<?>> arguments;

    public PythonMethod(String name, List<PythonMethodArgument<?>> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    public PythonMethod(String name) {
        this(name, List.of());
    }

    public String getName() {
        return name;
    }

    public List<PythonMethodArgument<?>> getArguments() {
        return arguments;
    }
}
