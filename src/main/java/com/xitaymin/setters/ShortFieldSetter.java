package com.xitaymin.setters;

public class ShortFieldSetter<T> extends NumberFieldSetter<T> {
    @Override
    protected Number getDefaultValue() {
        return Short.MIN_VALUE;
    }

    @Override
    protected Number parseTypeValue(String valueFromCsv) {
        return Short.parseShort(valueFromCsv);
    }
}
