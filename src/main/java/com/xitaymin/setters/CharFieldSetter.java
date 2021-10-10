package com.xitaymin.setters;

public class CharFieldSetter<T> extends OtherFieldSetter<T> {
    @Override
    protected Object getSpecificValue(String valueFromCsv) {
        return valueFromCsv.charAt(0);
    }

    @Override
    protected Object getDefaultValue() {
        return Character.MIN_VALUE;
    }

    @Override
    protected boolean isValueFromCsvInvalid(String valueFromCsv) {
        return valueFromCsv.length() > 1 || valueFromCsv.isBlank();
    }
}
