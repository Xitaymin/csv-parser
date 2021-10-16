package com.xitaymin.setters;

public class FloatFieldSetter<T> extends NumberFieldSetter<T> {
    @Override
    protected Number getDefaultValue() {
        return Float.MIN_VALUE;
    }

    @Override
    protected Number parseTypeValue(String valueFromCsv) {
        return Float.parseFloat(valueFromCsv);
    }
}
