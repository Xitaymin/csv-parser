package com.xitaymin.setters;

import java.lang.reflect.Field;

public class ByteFieldSetter<T> extends NumberFieldSetter<T> {

    @Override
    protected Object getDefaultValue() {
        return Byte.MIN_VALUE;
    }

    @Override
    protected void setSpecificValue(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        Byte value = Byte.parseByte(valueFromCsv);
        field.set(target, value);
    }
}
