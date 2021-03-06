package com.xitaymin.setters;

import java.lang.reflect.Field;

public class IntFieldSetter<T> extends NumberFieldSetter<T> {
    @Override
    protected Object getDefaultValue() {
        return Integer.MIN_VALUE;
    }

    @Override
    protected void setSpecificValue(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        Integer value = Integer.parseInt(valueFromCsv);
        field.set(target, value);
    }
}

