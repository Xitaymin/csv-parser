package com.xitaymin.setters;

import com.xitaymin.CsvHeader;
import com.xitaymin.exeptions.RequiredValueAbsentException;

import java.lang.reflect.Field;

public class ShortFieldSetter<T> implements FieldSetter<T> {
    @Override
    public void setField(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
        short value;
        try {
            value = Short.parseShort(valueFromCsv);
        } catch (NumberFormatException e) {
            if (csvHeader.required()) {
                throw new RequiredValueAbsentException(String.format("Required value for field %s with header %s is absent in csv file", field.getName(), csvHeader.name()));
            } else field.setShort(target, Short.MIN_VALUE);
            return;
        }
        field.setShort(target, value);

    }
}
