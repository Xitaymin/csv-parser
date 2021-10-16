package com.xitaymin.setters;

public class IntFieldSetter<T> extends NumberFieldSetter<T> {
    @Override
    protected Number getDefaultValue() {
        return Integer.MIN_VALUE;
    }

    @Override
    protected Number parseTypeValue(String valueFromCsv) {
        return Integer.parseInt(valueFromCsv);
    }
}

