package com.xitaymin.setters;

import java.lang.reflect.Field;

public class LongFieldSetter<T> extends NumberFieldSetter<T> {

    @Override
    protected Object getDefaultValue() {
        return Long.MIN_VALUE;
    }

    @Override
    protected void setSpecificValue(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        Long value = Long.parseLong(valueFromCsv);
        field.set(target, value);
    }
}

