package com.freelance.becker.japy.api;

import com.freelance.becker.japy.api.exception.NotMappableException;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MethodReturnValue {

    private Object returnValue;
    private String className;

    public MethodReturnValue() {
    }

    protected MethodReturnValue(String className, Object returnValue) {
        this.returnValue = returnValue;
        this.className = className;
    }

    private static <T> T castWithJson(Object object, Class<T> expectedClass) {
        Gson gson = new Gson();
        return gson.fromJson(gson.toJson(object), expectedClass);
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public String getClassName() {
        return className;
    }

    public <T> T castExactly(Class<T> expectedClass) {
        return castWithJson(returnValue, expectedClass);
    }

    public <T> List<T> castListWithSameClassesExactly(Class<T> expectedClass) {
        if (returnValue instanceof List<?> list) {
            List<T> casted = new ArrayList<>();
            for (Object object : list) {
                casted.add(castWithJson(object, expectedClass));
            }
            return casted;
        }

        throw new NotMappableException(returnValue.getClass());
    }

    public List<Object> castListWithDifferentClassesExactly(Class<?>... expectedClasses) {
        if (returnValue instanceof List<?> list) {
            if (list.size() != expectedClasses.length) {
                throw new IllegalArgumentException("Expected number of casts was " + list.size() + " but was " + expectedClasses.length);
            }
            List<Object> casted = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                casted.add(castWithJson(list.get(i), expectedClasses[i]));
            }
            return casted;
        }

        throw new NotMappableException(returnValue.getClass());
    }

    public <T> T mapCustom(MethodReturnValueMapper<T> mapper) {
        if (returnValue instanceof Map<?, ?> source)
            return mapper.map((Map<String, Object>) source);

        throw new NotMappableException(returnValue.getClass());
    }

    @Override
    public String toString() {
        return "MethodReturnValue{" +
                "className='" + className + '\'' +
                ", returnValue=" + returnValue +
                '}';
    }
}
