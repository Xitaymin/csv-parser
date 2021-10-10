package com.xitaymin.setters;

import java.lang.reflect.Field;

public class DoubleFieldSetter<T> extends NumberFieldSetter<T> {
    @Override
    protected Object getDefaultValue() {
        return Double.MIN_VALUE;
    }

    @Override
    protected void setSpecificValue(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        Double value = Double.parseDouble(valueFromCsv);
        field.set(target, value);
    }
}

