package com.freelance.becker.japy.api;

import java.util.Map;

public interface MethodReturnValueMapper<T> {

    /**
     * Custom implementation possible to map a Map in a custom Object.
     *
     * @param source Map
     * @return T
     */
    public T map(Map<String, Object> source);
}
