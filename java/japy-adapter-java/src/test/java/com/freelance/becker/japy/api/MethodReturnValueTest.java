package com.freelance.becker.japy.api;

import com.freelance.becker.japy.api.exception.NotMappableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MethodReturnValueTest {

    MethodReturnValue stringMethodReturnValue;
    MethodReturnValue complexObjectReturnValue;
    MethodReturnValue complexObjectInListReturnValue;
    MethodReturnValue differentObjectsInListReturnValue;
    MethodReturnValue nonExistingReturnVale;
    ComplexSubClass complexSubClass;
    ComplexSubClass complexSubClass2;
    ComplexBaseClass complexBaseClass;

    @BeforeEach
    void setUp() {
        complexSubClass = new ComplexSubClass("arg1", "arg2", "arg3", "arg4");
        complexSubClass2 = new ComplexSubClass("ARG1", "ARG2", "ARG3", "ARG4");
        complexBaseClass = new ComplexBaseClass("1arg", "2arg", "3arg");

        stringMethodReturnValue = new MethodReturnValue("string", "Test");
        complexObjectReturnValue = new MethodReturnValue("ComplexSubClass", complexSubClass);
        complexObjectInListReturnValue = new MethodReturnValue("List", List.of(complexSubClass, complexSubClass2));
        differentObjectsInListReturnValue = new MethodReturnValue("List", List.of(complexSubClass, complexBaseClass, complexSubClass2));

        Map<String, String> nonExistingMap = new HashMap<>();
        nonExistingMap.put("someArg", "someVal");
        nonExistingReturnVale = new MethodReturnValue("NonExistingClass", nonExistingMap);
    }

    @Test
    void testGetClassName() {

        assertEquals("string", stringMethodReturnValue.getClassName());
    }

    @Test
    void testGetReturnValue() {
        assertEquals("Test", stringMethodReturnValue.getReturnValue());
    }

    @Test
    void testCastExactlyWithCorrectObject() {
        ComplexSubClass actualComplexSubClass = complexObjectReturnValue.castExactly(ComplexSubClass.class);

        assertEquals(complexSubClass, actualComplexSubClass);
    }

    @Test
    void testCastExactlyWithWrongObject() {
        ComplexSubClass actualComplexSubClass = nonExistingReturnVale.castExactly(ComplexSubClass.class);

        ComplexSubClass expectedComplexSubClass = new ComplexSubClass(null, null, null, null, null);

        assertEquals(expectedComplexSubClass, actualComplexSubClass);
    }

    @Test
    void testCastListWithSameClassesExactly() {
        List<ComplexSubClass> objects = complexObjectInListReturnValue.castListWithSameClassesExactly(ComplexSubClass.class);

        assertEquals(2, objects.size());
        assertEquals(complexSubClass, objects.get(0));
        assertEquals(complexSubClass2, objects.get(1));
    }

    @Test
    void testCastListWithSameClassesExactlyThrowsExceptionOnNonListType() {

        assertThrows(NotMappableException.class, () -> stringMethodReturnValue.castListWithSameClassesExactly(Object.class));
    }

    @Test
    void testCastListWithDifferentClassesExactlyThrowsExceptionOnNonListType() {
        assertThrows(NotMappableException.class, () -> stringMethodReturnValue.castListWithDifferentClassesExactly(Object.class));

    }


    @Test
    void testCastListWithDifferentClassesExactlyThrowsExceptionOnUnexpectedArgumentsLength() {
        assertThrows(IllegalArgumentException.class, () -> differentObjectsInListReturnValue.castListWithDifferentClassesExactly(Object.class, ComplexBaseClass.class));

    }


    @Test
    void testCastListWithDifferentClassesExactlyThrowsException() {
        List<Object> objects = differentObjectsInListReturnValue.castListWithDifferentClassesExactly(ComplexSubClass.class, ComplexBaseClass.class, ComplexSubClass.class);

        assertEquals(3, objects.size());
        assertInstanceOf(ComplexSubClass.class, objects.get(0));
        assertInstanceOf(ComplexBaseClass.class, objects.get(1));
        assertInstanceOf(ComplexSubClass.class, objects.get(2));
    }

    @Test
    void testMapCustomThrowsExceptionOnNonMapValue() {
        assertThrows(NotMappableException.class, () -> stringMethodReturnValue.mapCustom(mock(MethodReturnValueMapper.class)));
    }

    @Test
    void testMapCustom() {
        MethodReturnValueMapper<?> mapper = mock(MethodReturnValueMapper.class);

        nonExistingReturnVale.mapCustom(mapper);

        verify(mapper, times(1)).map(anyMap());
    }

    @Test
    void testDefaultConstructorExists() {
        MethodReturnValue methodReturnValue = new MethodReturnValue();

        assertNotNull(methodReturnValue);
    }
}