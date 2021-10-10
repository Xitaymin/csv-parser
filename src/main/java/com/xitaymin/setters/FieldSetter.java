package com.xitaymin.setters;

import java.lang.reflect.Field;

public interface FieldSetter<T> {
    String REQUIRED_FIELD_VALUE_ABSENT = "Required value for field %s with header %s is absent in csv file or can't be parsed.";
    void setField(String valueFromCsv, T target, Field field) throws IllegalAccessException;
}
