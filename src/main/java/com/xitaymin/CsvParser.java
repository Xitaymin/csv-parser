package com.xitaymin;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CsvParser {
    private final File csvContainer;
    Map<Field, String> fieldsWithRequiredHeaders = new LinkedHashMap<>();
    Map<Field, String> fieldsWithAllHeaders = new LinkedHashMap<>();
    Map<String, Integer> headersWithIndexes = new HashMap<>();
    private Scanner scanner;

    public CsvParser(String filePath) {
        this.csvContainer = new File(filePath);
        //todo check if file exists
    }

    public <T> List<T> parseLines(Class<T> tClass) throws FileNotFoundException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        List<T> list = new ArrayList<>();
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields
        ) {
            if (field.isAnnotationPresent(CsvHeader.class)) {
                CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
                if (csvHeader.required()) {
                    fieldsWithRequiredHeaders.put(field, csvHeader.name());
                } else fieldsWithAllHeaders.put(field, csvHeader.name());
            }
        }

        fieldsWithAllHeaders.putAll(fieldsWithRequiredHeaders);

        if (isFileAvailable()) {
            scanner = new Scanner(csvContainer);
            parseHeader(scanner);

            while (scanner.hasNextLine()) {
                //todo fix this
//                    T t = (T)Class.forName(tClass.getName());
                T t = tClass.getDeclaredConstructor().newInstance();
                String valuesLine = scanner.nextLine();
                String[] values = valuesLine.split(",");
                for (Field field : fieldsWithRequiredHeaders.keySet()) {
                    Class fieldType = field.getType();
                    field.setAccessible(true);
                    //todo find a way to set fields of unknown types
                }
            }

        } else throw new RuntimeException("File is not available.");

        // todo getFile


    }

        return null;
}

    private Map<String, Integer> parseHeader(Scanner scanner) {
        String headerLine = scanner.nextLine();
        String[] headers = headerLine.split(",");
        List<String> list = Arrays.asList(headers);
        if (list.containsAll(fieldsWithRequiredHeaders.values())) {
            for (int i = 0; i < headers.length - 1; i++) {
                for (String header : fieldsWithAllHeaders.values()
                ) {
                    if (header.equals(headers[i])) {
                        headersWithIndexes.put(header, i);
                    }
                }
            }


        } else throw new RuntimeException("Csv file doesn't contains all required fields");


        return null;
    }

    private boolean isFileAvailable() {
        return csvContainer.exists() && csvContainer.canRead();
    }
}

