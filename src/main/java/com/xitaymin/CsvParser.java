package com.xitaymin;

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
                    boolean required = annotation.required();
                    String header = annotation.name();
                    String valueFromCsv = values[headersWithIndexes.get(header)];
                    field.setAccessible(true);

                    if (fieldType.equals("int") || fieldType.equals("Integer")) {
                        int value;
                        try {
                            value = Integer.parseInt(valueFromCsv);
                        } catch (NumberFormatException e) {
                            if (required) {
                                throw new RequiredValueAbsentException(String.format("Required value for field %s with header %s is absent in csv file", field.getName(), annotation.name()));
                            } else field.setInt(t, Integer.MIN_VALUE);
                            continue;
                        }
                        field.setInt(t, value);
                    } else if (fieldType.equals("String")) {
                        field.set(t, valueFromCsv);
                    } else if (fieldType.equals("boolean") || fieldType.equals("Boolean")) {
                        if (valueFromCsv.equals("true") || valueFromCsv.equals("false")) {
                            boolean value = Boolean.parseBoolean(valueFromCsv);
                            field.setBoolean(t, value);
                        } else if (required) {
                            throw new RequiredValueAbsentException(String.format("Required value for field %s with header %s is absent in csv file", field.getName(), annotation.name()));
                        } else field.setBoolean(t, false);
                    }
                }
                list.add(t);
            }
        }
        return list;
//        } else throw new RuntimeException("File is not available.");
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

