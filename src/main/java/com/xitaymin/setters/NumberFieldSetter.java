package com.xitaymin.setters;

import com.xitaymin.CsvHeader;
import com.xitaymin.exeptions.RequiredValueAbsentException;

import java.lang.reflect.Field;

public abstract class NumberFieldSetter<T> implements FieldSetter<T> {
    @Override
    public final void setField(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
        try {
            field.set(target, parseTypeValue(valueFromCsv));

        } catch (NumberFormatException e) {
            if (csvHeader.required()) {
                throw new RequiredValueAbsentException(String.format(REQUIRED_FIELD_VALUE_ABSENT, field.getName(), csvHeader.name()));
            } else field.set(target, getDefaultValue());
        }
    }

    protected abstract Number getDefaultValue();

    protected abstract Number parseTypeValue(String valueFromCsv);
}
