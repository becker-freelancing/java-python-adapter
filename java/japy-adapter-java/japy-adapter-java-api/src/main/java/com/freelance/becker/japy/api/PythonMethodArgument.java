package com.freelance.becker.japy.api;

public class PythonMethodArgument <T> {

    private T argument;

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
