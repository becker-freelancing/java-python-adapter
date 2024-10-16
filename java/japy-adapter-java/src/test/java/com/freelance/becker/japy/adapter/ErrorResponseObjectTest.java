package com.freelance.becker.japy.adapter;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseObjectTest {

    static String errorString =
            """
            {
                "error": "This is an error",
                "existingMethods": ["test_method", "i_am_a_method"]
            }         
            """;

    ErrorResponseObject errorResponseObject;

    @BeforeEach
    void setUp(){
        errorResponseObject = new Gson().fromJson(errorString, ErrorResponseObject.class);
    }

    @Test
    void getError() {
        assertEquals("This is an error", errorResponseObject.getError());
    }

    @Test
    void getExistingMethods() {
        assertEquals(List.of("test_method", "i_am_a_method"), errorResponseObject.getExistingMethods());
    }
}