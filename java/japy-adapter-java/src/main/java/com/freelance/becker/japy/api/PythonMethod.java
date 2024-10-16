package com.freelance.becker.japy.api;

import java.util.List;

public class PythonMethod {


    private String name;
    private List<PythonMethodArgument<?>> arguments;

    /**
     * For methods with arguments. These arguments are provided by the second parameter arguments.
     *
     * @param name      String
     * @param arguments List
     */
    public PythonMethod(String name, List<PythonMethodArgument<?>> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    /**
     * For void methods or methods where all arguments are optional.
     *
     * @param name String
     */
    public PythonMethod(String name) {
        this(name, List.of());
    }

    public String getName() {
        return name;
    }

    public List<PythonMethodArgument<?>> getArguments() {
        return arguments;
    }

    @Override
    public String toString() {
        return "PythonMethod{" +
                "name='" + name + '\'' +
                ", arguments=" + arguments +
                '}';
    }
}
