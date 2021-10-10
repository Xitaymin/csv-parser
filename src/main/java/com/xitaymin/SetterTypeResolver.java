package com.xitaymin;

import com.xitaymin.setters.BooleanFieldSetter;
import com.xitaymin.setters.FieldSetter;
import com.xitaymin.setters.IntFieldSetter;
import com.xitaymin.setters.StringSetter;

import java.util.HashMap;
import java.util.Map;

public class SetterTypeResolver<T> {
    private Map<String, FieldSetter<T>> setters = new HashMap<>();

    public SetterTypeResolver() {
        setters.put("int", new IntFieldSetter<>());
        setters.put("Integer", new IntFieldSetter<>());
        setters.put("String", new StringSetter<>());
        setters.put("boolean", new BooleanFieldSetter<>());
        setters.put("Boolean", new BooleanFieldSetter<>());
    }

    public FieldSetter<T> resolveSetter(String fieldType) {
        return setters.get(fieldType);
    }
}
