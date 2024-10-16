package com.freelance.becker.japy.api;

public class PythonMethodArgument <T> {

    private T argument;

    /**
     * Any object as argument for a method in Python.
     *
     * @param argument T
     */
    public PythonMethodArgument(T argument) {
        this.argument = argument;
    }

    public T getArgument() {
        return argument;
    }

    @Override
    public String toString() {
        return "PythonMethodArgument{" +
                "argument=" + argument +
                '}';
    }
}
