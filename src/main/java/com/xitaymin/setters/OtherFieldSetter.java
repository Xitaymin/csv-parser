package com.xitaymin.setters;

import com.xitaymin.CsvHeader;
import com.xitaymin.exeptions.RequiredValueAbsentException;

import java.lang.reflect.Field;

public abstract class OtherFieldSetter<T> implements FieldSetter<T> {
    @Override
    public final void setField(String valueFromCsv, T target, Field field) throws IllegalAccessException {
        CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
        if (isValueFromCsvInvalid(valueFromCsv)) {
            if (csvHeader.required()) {
                throw new RequiredValueAbsentException(String.format(REQUIRED_FIELD_VALUE_ABSENT, field.getName(), csvHeader.name()));
            } else field.set(target, getDefaultValue());
            return;
        }
        field.set(target, getSpecificValue(valueFromCsv));

    }

    protected abstract Object getSpecificValue(String valueFromCsv);

    protected abstract Object getDefaultValue();

    protected abstract boolean isValueFromCsvInvalid(String valueFromCsv);
}
