package com.xitaymin.setters;

public class ByteFieldSetter<T> extends NumberFieldSetter<T> {

    @Override
    protected Number getDefaultValue() {
        return Byte.MIN_VALUE;
    }

    @Override
    protected Number parseTypeValue(String valueFromCsv) {
        return Byte.parseByte(valueFromCsv);
    }
}
