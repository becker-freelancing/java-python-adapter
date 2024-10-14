package com.freelance.becker.japy.api;

//TODO: Generics needed?
public class PythonMethodArgument <T> {

    private T argument;

    public PythonMethodArgument(T argument) {
        this.argument = argument;
    }

    public T getArgument() {
        return argument;
    }
}
