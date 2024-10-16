package com.freelance.becker.japy.api;

import java.util.Objects;

public class ComplexSubClass extends ComplexBaseClass {

    protected String arg4;
    protected ComplexBaseClass inner_complex;

    public ComplexSubClass(String arg1, String arg2, String arg3, String arg4) {
        super(arg1, arg2, arg3);
        this.arg4 = arg4;
    }

    public ComplexSubClass(String arg1, String arg2, String arg3, String arg4, ComplexBaseClass inner_complex) {
        super(arg1, arg2, arg3);
        this.arg4 = arg4;
        this.inner_complex = inner_complex;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        ComplexSubClass that = (ComplexSubClass) object;
        return Objects.equals(arg4, that.arg4) && Objects.equals(inner_complex, that.inner_complex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(arg4, inner_complex);
    }

    @Override
    public String toString() {
        return "ComplexSubClass{" +
                "arg4='" + arg4 + '\'' +
                ", inner_complex=" + inner_complex +
                ", arg1='" + arg1 + '\'' +
                ", arg2='" + arg2 + '\'' +
                ", arg3='" + arg3 + '\'' +
                '}';
    }
}
