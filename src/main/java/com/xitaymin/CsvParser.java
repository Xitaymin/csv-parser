package com.xitaymin;

import com.xitaymin.exeptions.CsvContainerNotAvailableException;
import com.xitaymin.exeptions.RequiredValueAbsentException;
import com.xitaymin.setters.FieldSetter;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CsvParser {
    private final Scanner scanner;
    private final Map<String, Integer> headersWithIndexes = new HashMap<>();
    private final Set<Field> annotatedFields = new HashSet<>();

    public CsvParser(String filePath) throws FileNotFoundException {
        File csvContainer = new File(filePath);
        if (isFileAvailable(csvContainer)) {
            scanner = new Scanner(csvContainer);
        } else
            throw new CsvContainerNotAvailableException(String.format("File with path %s doesn't exist or can't be read.", filePath));
    }

    private static boolean isFileAvailable(File file) {
        return file.exists() && file.canRead();
    }

    public <T> List<T> parseLines(Class<T> tClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        List<T> list = new ArrayList<>();
        getAnnotatedFields(tClass);
        parseHeader();
        while (scanner.hasNextLine()) {
            String valuesLine = scanner.nextLine();
            if (!valuesLine.isBlank()) {
                T t = tClass.getDeclaredConstructor().newInstance();
                String[] values = valuesLine.split(",", -1);
                //possible types примитив, боксовый тип или строка
                for (Field field : annotatedFields) {
                    String fieldType = field.getType().getSimpleName();
                    CsvHeader annotation = field.getAnnotation(CsvHeader.class);
                    String valueFromCsv = values[headersWithIndexes.get(annotation.name())];
                    field.setAccessible(true);

                    FieldSetter<T> fieldSetter = new SetterTypeResolver<T>().resolveSetter(fieldType);
                    fieldSetter.setField(valueFromCsv, t, field);
                }
                list.add(t);
            }
        }
        return list;
    }

    private <T> void getAnnotatedFields(Class<T> tClass) {
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CsvHeader.class)) {
                annotatedFields.add(field);
            }
        }
    }

    private void parseHeader() {
        String headerLine = scanner.nextLine();
        String[] headers = headerLine.split(",");
        List<String> list = Arrays.asList(headers);

        Set<String> requiredHeaders = getRequiredHeaders();
        if (list.containsAll(requiredHeaders)) {
            for (int i = 0; i < headers.length; i++) {
                for (Field field : annotatedFields) {
                    CsvHeader annotation = field.getAnnotation(CsvHeader.class);
                    String name = annotation.name();
                    if (name.equals(headers[i])) {
                        headersWithIndexes.put(name, i);
                    }
                }
            }
        } else throw new RequiredValueAbsentException("Csv file doesn't contains all required field headers");
    }

    private Set<String> getRequiredHeaders() {
        Set<String> requiredHeaders = new HashSet<>();
        for (Field field : annotatedFields) {
            CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
            if (csvHeader.required()) {
                requiredHeaders.add(csvHeader.name());
            }
        }
        return requiredHeaders;
    }
}

