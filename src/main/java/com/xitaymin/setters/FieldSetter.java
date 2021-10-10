package com.xitaymin.setters;

import java.lang.reflect.Field;

public interface FieldSetter<T> {
    void setField(String valueFromCsv, T target, Field field) throws IllegalAccessException;
}
