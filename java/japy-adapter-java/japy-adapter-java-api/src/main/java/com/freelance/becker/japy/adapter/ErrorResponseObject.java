package com.freelance.becker.japy.adapter;

import java.util.List;

class ErrorResponseObject {

    private String error;

    private List<String> existingMethods;

    public String getError() {
        return error;
    }

    public List<String> getExistingMethods() {
        return existingMethods;
    }
}
