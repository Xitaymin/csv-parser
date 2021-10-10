package com.xitaymin;

import com.xitaymin.setters.*;

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
        setters.put("Long", new LongFieldSetter<>());
        setters.put("long", new LongFieldSetter<>());
        setters.put("short", new ShortFieldSetter<>());

    }

    public FieldSetter<T> resolveSetter(String fieldType) {
        return setters.get(fieldType);
    }
}
