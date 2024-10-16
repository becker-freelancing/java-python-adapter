package com.freelance.becker.japy.adapter;

public class NoPythonServerPortFoundException extends RuntimeException {

    NoPythonServerPortFoundException(int startPort, int endPort) {
        super("No Japy server found in port range (" + startPort + ", " + endPort + ")");
    }
}
