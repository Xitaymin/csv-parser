package com.xitaymin.setters;

public class LongFieldSetter<T> extends NumberFieldSetter<T> {

    @Override
    protected Number parseTypeValue(String valueFromCsv) {
        return Long.parseLong(valueFromCsv);
    }

    @Override
    protected Number getDefaultValue() {
        return Long.MIN_VALUE;
    }
}

