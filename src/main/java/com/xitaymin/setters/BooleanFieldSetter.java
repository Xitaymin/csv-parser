package com.xitaymin.setters;

public class BooleanFieldSetter<T> extends OtherFieldSetter<T> {
    @Override
    protected Object getSpecificValue(String valueFromCsv) {
        return Boolean.parseBoolean(valueFromCsv);
    }

    @Override
    protected Object getDefaultValue() {
        return false;
    }

    @Override
    protected boolean isValueFromCsvInvalid(String valueFromCsv) {
        return !(valueFromCsv.equals("true") || valueFromCsv.equals("false"));
    }
}

