package com.xitaymin.setters;

import java.lang.reflect.Field;

public class ShortFieldSetter<T> extends NumberFieldSetter<T> {
    @Override
    protected Object getDefaultValue() {
        return Short.MIN_VALUE;
    }

    @Override
    protected void setSpecificValue(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        Short value = Short.parseShort(valueFromCsv);
        field.set(target, value);
    }
}
