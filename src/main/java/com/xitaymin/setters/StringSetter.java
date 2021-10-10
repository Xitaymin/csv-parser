package com.xitaymin.setters;

import java.lang.reflect.Field;

public class StringSetter<T> implements FieldSetter<T> {
    @Override
    public void setField(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        //todo process exceptions
        field.set(target, valueFromCsv);
    }
}
