package com.freelance.becker.japy.runner;

public class PythonRunnerFactory {

    private static PythonRunner instance;

    public static PythonRunner getInstance(){
        if (instance == null){
            instance = new PythonRunnerImpl();
        }

        return instance;
    }
}
