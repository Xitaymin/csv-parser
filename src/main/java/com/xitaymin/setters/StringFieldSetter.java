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

//    @Override
//    public void setField(String valueFromCsv, T target, Field field) throws IllegalAccessException {
//        CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
//        if(valueFromCsv.isBlank()){
//            if(csvHeader.required()){
//                throw new RequiredValueAbsentException(String.format(REQUIRED_FIELD_VALUE_ABSENT, field.getName(), csvHeader.name()));
//            }
//            else field.set(target, DEFAULT_STRING);
//            return;
//        }
//        field.set(target, valueFromCsv);
//    }
}
