package com.xitaymin.setters;

import com.xitaymin.CsvHeader;
import com.xitaymin.exeptions.RequiredValueAbsentException;

import java.lang.reflect.Field;

public class BooleanFieldSetter<T> implements FieldSetter<T> {
    @Override
    public void setField(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
        if (valueFromCsv.equals("true") || valueFromCsv.equals("false")) {
            boolean value = Boolean.parseBoolean(valueFromCsv);
            field.setBoolean(target, value);
        } else if (csvHeader.required()) {
            throw new RequiredValueAbsentException(String.format("Required value for field %s with header %s is absent in csv file", field.getName(), csvHeader.name()));
        } else field.setBoolean(target, false);
    }
}

