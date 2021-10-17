package com.xitaymin;

import com.xitaymin.setters.BooleanFieldSetter;
import com.xitaymin.setters.ByteFieldSetter;
import com.xitaymin.setters.CharFieldSetter;
import com.xitaymin.setters.DoubleFieldSetter;
import com.xitaymin.setters.FieldSetter;
import com.xitaymin.setters.FloatFieldSetter;
import com.xitaymin.setters.IntFieldSetter;
import com.xitaymin.setters.LongFieldSetter;
import com.xitaymin.setters.ShortFieldSetter;
import com.xitaymin.setters.StringFieldSetter;

import java.util.HashMap;
import java.util.Map;

public class SetterTypeResolver<T> {
    private final Map<String, FieldSetter<T>> setters = new HashMap<>();

    public SetterTypeResolver() {
        final FieldSetter<T> intSetter = new IntFieldSetter<>();
        final FieldSetter<T> booleanSetter = new BooleanFieldSetter<>();
        final FieldSetter<T> longSetter = new LongFieldSetter<>();
        final FieldSetter<T> shortSetter = new ShortFieldSetter<>();
        final FieldSetter<T> byteSetter = new ByteFieldSetter<>();
        final FieldSetter<T> charSetter = new CharFieldSetter<>();
        final FieldSetter<T> floatSetter = new FloatFieldSetter<>();
        final FieldSetter<T> doubleSetter = new DoubleFieldSetter<>();


        setters.put("int", intSetter);
        setters.put("Integer", intSetter);
        setters.put("String", new StringFieldSetter<>());
        setters.put("boolean", booleanSetter);
        setters.put("Boolean", booleanSetter);
        setters.put("Long", longSetter);
        setters.put("long", longSetter);
        setters.put("short", shortSetter);
        setters.put("Short", shortSetter);
        setters.put("byte", byteSetter);
        setters.put("Byte", byteSetter);
        setters.put("char", charSetter);
        setters.put("Character", charSetter);
        setters.put("float", floatSetter);
        setters.put("Float", floatSetter);
        setters.put("double", doubleSetter);
        setters.put("Double", doubleSetter);
    }

    public FieldSetter<T> resolveSetter(String fieldType) {
        return setters.get(fieldType);
    }
}
