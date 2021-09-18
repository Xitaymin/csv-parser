package com.xitaymin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CsvParser {
    private String filePath;

    public CsvParser(String filePath) {
        this.filePath = filePath;
        //todo check if file exists
    }

    public <T> List<T> parseLines(Class<T> tClass) {
        Map<Field, String> names = new LinkedHashMap<>();
        List<T> list = new ArrayList<>();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields
        ) {
            if (field.isAnnotationPresent(CsvHeader.class)) {
                CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
                if (csvHeader.required()) {
                    names.put(field, csvHeader.name());
                }
            }
            ;

        }

        // todo getFile


    }
}

