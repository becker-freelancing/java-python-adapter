package com.freelance.becker.japy.api;

import java.util.Map;

public interface MethodReturnValueMapper<T> {

    public T map(Map<String, Object> source);
}
