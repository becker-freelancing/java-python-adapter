package com.freelance.becker.japy.adapter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PythonAdapterTest {

    @Test
    void testDefaultAdapterIsCorrectInstance(){
        assertInstanceOf(PythonAdapterImpl.class, PythonAdapter.getDefault());
    }

}