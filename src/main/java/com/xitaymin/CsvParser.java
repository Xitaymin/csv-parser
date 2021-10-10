package com.xitaymin;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CsvParser {
    private final File csvContainer;
    Map<String, Integer> headersWithIndexes = new HashMap<>();
    private Scanner scanner;
    private Map<Field, CsvHeader> fieldsWithAnnotations = new HashMap<>();

    public CsvParser(String filePath) {
        this.csvContainer = new File(filePath);
        //todo check if file exists
    }

    public <T> List<T> parseLines(Class<T> tClass) throws FileNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        List<T> list = new ArrayList<>();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CsvHeader.class)) {
                fieldsWithAnnotations.put(field, field.getAnnotation(CsvHeader.class));
            }
        }

        if (isFileAvailable()) {
            scanner = new Scanner(csvContainer);
            parseHeader(scanner);

            //todo check for blank Strings
            while (scanner.hasNextLine()) {
                String valuesLine = scanner.nextLine();
                if (!valuesLine.isBlank()) {
                    T t = tClass.getDeclaredConstructor().newInstance();

                    String[] values = valuesLine.split(",");

                    //possible types примитив, боксовый тип или строка

                    for (Field field : fieldsWithAnnotations.keySet()) {
                        String fieldType = field.getType().getSimpleName();
                        CsvHeader annotation = fieldsWithAnnotations.get(field);
                        boolean required = annotation.required();
                        String header = annotation.name();
                        String valueFromCsv = values[headersWithIndexes.get(header)];
                        field.setAccessible(true);

                        if (fieldType.equals("int") || fieldType.equals("Integer")) {
                            Integer value;
                            try {
                                value = Integer.valueOf(valueFromCsv);
                            } catch (NumberFormatException e) {
                                if (required) {
                                    throw new RuntimeException("Required field value is absent in csv file");
                                } else field.setInt(t, Integer.MAX_VALUE);
                                continue;
                            }
                            field.setInt(t, value);
                        } else if (fieldType.equals("String")) {
                            field.set(t, valueFromCsv);
                        } else if (fieldType.equals("boolean") || fieldType.equals("Boolean")) {
                            Boolean value = Boolean.valueOf(valueFromCsv);
                            field.setBoolean(t, value);
                        }
                    }
                    list.add(t);
                }
            }
            return list;
            // todo getFile
        } else throw new RuntimeException("File is not available.");
    }

    private Map<String, Integer> parseHeader(Scanner scanner) {
        String headerLine = scanner.nextLine();
        String[] headers = headerLine.split(",");
        List<String> list = Arrays.asList(headers);

        Set<String> requiredHeaders = getRequiredHeaders();
        if (list.containsAll(requiredHeaders)) {
            for (int i = 0; i < headers.length; i++) {
                for (CsvHeader annotation : fieldsWithAnnotations.values()) {
                    if (annotation.name().equals(headers[i])) {
                        headersWithIndexes.put(annotation.name(), i);
                    }
                }
            }
        } else throw new RuntimeException("Csv file doesn't contains all required fields");

        return null;
    }

    private Set<String> getRequiredHeaders() {
        Set<String> requiredHeaders = new HashSet<>();
        for (Field field : fieldsWithAnnotations.keySet()) {
            CsvHeader csvHeader = fieldsWithAnnotations.get(field);
            if (csvHeader.required()) {
                requiredHeaders.add(csvHeader.name());
            }
        }
        return requiredHeaders;
    }

    private boolean isFileAvailable() {
        return csvContainer.exists() && csvContainer.canRead();
    }
}

