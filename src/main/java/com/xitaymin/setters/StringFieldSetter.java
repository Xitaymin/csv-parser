package com.xitaymin.setters;

public class StringFieldSetter<T> extends OtherFieldSetter<T> {
    private static final String DEFAULT_STRING = "Default string";

    @Override
    protected Object getSpecificValue(String valueFromCsv) {
        return valueFromCsv;
    }

    @Override
    protected Object getDefaultValue() {
        return DEFAULT_STRING;
    }

    @Override
    protected boolean isValueFromCsvInvalid(String valueFromCsv) {
        return valueFromCsv.isBlank();
    }

}
