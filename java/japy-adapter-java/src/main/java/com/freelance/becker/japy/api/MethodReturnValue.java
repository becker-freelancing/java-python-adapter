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

    /**
     * Transforms the return value into an Object of the given class. Attributes in this class which are not in return value are ignored and initialized with null.
     *
     * @param expectedClass Class
     * @return T
     * @param <T>
     */
    public <T> T castExactly(Class<T> expectedClass) {
        return castWithJson(returnValue, expectedClass);
    }

    /**
     * If the return value is a List wich contains elements of the same class, then this Method maps each object according to castExactly.
     * @param expectedClass Class
     * @return List
     * @param <T>
     */
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

    /**
     *  If the List does contain more than one object, for each object in that List its type must be provided. Then each item in the List is mapped at its own according to castExactly.
     * @param expectedClasses Class...
     * @return List
     */
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

    /**
     * You can also define a Mapper by yourself. This mapper accepts a Map which represents an Object in JSON format.
     * @param mapper MethodReturnValueMapper
     * @return T
     * @param <T>
     */
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
