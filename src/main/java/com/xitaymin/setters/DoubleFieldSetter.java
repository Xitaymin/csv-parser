package com.xitaymin.setters;

public class DoubleFieldSetter<T> extends NumberFieldSetter<T> {
    @Override
    protected Number getDefaultValue() {
        return Double.MIN_VALUE;
    }

    @Override
    protected Number parseTypeValue(String valueFromCsv) {
        return Double.parseDouble(valueFromCsv);
    }
}

