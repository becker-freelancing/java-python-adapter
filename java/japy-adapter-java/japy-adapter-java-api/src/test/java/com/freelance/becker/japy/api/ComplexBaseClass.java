package com.freelance.becker.japy.api;

import java.util.Objects;

public class ComplexBaseClass {

    protected String arg1;
    protected String arg2;
    protected String arg3;

    public ComplexBaseClass(String arg1, String arg2, String arg3) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ComplexBaseClass that = (ComplexBaseClass) object;
        return Objects.equals(arg1, that.arg1) && Objects.equals(arg2, that.arg2) && Objects.equals(arg3, that.arg3);
    }

    @Override
    public String toString() {
        return "ComplexBaseClass{" +
                "arg1='" + arg1 + '\'' +
                ", arg2='" + arg2 + '\'' +
                ", arg3='" + arg3 + '\'' +
                '}';
    }
}
