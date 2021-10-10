package com.xitaymin.setters;

import java.lang.reflect.Field;

public class FloatFieldSetter<T> extends NumberFieldSetter<T> {
    @Override
    protected Object getDefaultValue() {
        return Float.MIN_VALUE;
    }

    @Override
    protected void setSpecificValue(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        Float value = Float.parseFloat(valueFromCsv);
        field.set(target, value);
    }
}
