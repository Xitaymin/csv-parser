package com.xitaymin;

import com.xitaymin.exeptions.CsvContainerNotAvailableException;
import com.xitaymin.exeptions.RequiredValueAbsentException;
import com.xitaymin.setters.FieldSetter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CsvParser {
    private static final String FILE_NOT_AVAILABLE = "File with path %s doesn't exist or can't be read.";
    private static final String REQUIRED_HEADERS_NOT_FOUND = "Csv file doesn't contains all required field headers";
    private final Reader reader;
    //    private final Map<String, Integer> headersWithIndexes = new HashMap<>();
    private final Map<String, Field> headersWithAnnotatedFields = new HashMap<>();

    public CsvParser(String filePath) throws IOException {
        File csvContainer = new File(filePath);
        if (isFileAvailable(csvContainer)) {
            reader = Files.newBufferedReader(Paths.get(filePath));
        } else throw new CsvContainerNotAvailableException(String.format(FILE_NOT_AVAILABLE, filePath));
    }

    private static boolean isFileAvailable(File file) {
        return file.exists() && file.canRead();
    }

    public <T> List<T> parseLines(Class<T> tClass)
            throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        CSVParser parser = new CSVParser(reader, csvFormat);
        List<String> headersInCsv = parser.getHeaderNames();

        defineFieldsWithAnnotation(tClass);

        if (headersInCsv.containsAll(getRequiredHeaders())) {
            List<T> parsedObjects = new ArrayList<>();
            Set<String> headersFromAnnotationInCsv = intersection(headersInCsv, headersWithAnnotatedFields.keySet());
            for (CSVRecord record : parser.getRecords()) {
                T t = tClass.getDeclaredConstructor()
                        .newInstance();
                for (String header : headersFromAnnotationInCsv) {
                    Field field = headersWithAnnotatedFields.get(header);
                    field.setAccessible(true);
                    String fieldType = field.getType()
                            .getSimpleName();
                    FieldSetter<T> fieldSetter = new SetterTypeResolver<T>().resolveSetter(fieldType);
                    fieldSetter.setField(record.get(header), t, field);
                }
                parsedObjects.add(t);
            }
            return parsedObjects;
        } else throw new RequiredValueAbsentException(REQUIRED_HEADERS_NOT_FOUND);
    }


//    public <T> List<T> parseLines(Class<T> tClass)
//            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
//
//        List<T> parsedObjects = new ArrayList<>();
//        getAnnotatedFields(tClass);
//        parseHeader();
//
//        while (scanner.hasNextLine()) {
//            String valuesLine = scanner.nextLine();
//            if (!valuesLine.isBlank()) {
//                T t = tClass.getDeclaredConstructor()
//                        .newInstance();
//                String[] values = valuesLine.split(",", -1);
//                for (Field field : annotatedFields) {
//                    String fieldType = field.getType()
//                            .getSimpleName();
//                    CsvHeader annotation = field.getAnnotation(CsvHeader.class);
//                    String valueFromCsv = values[headersWithIndexes.get(annotation.name())];
//                    field.setAccessible(true);
//
//                    FieldSetter<T> fieldSetter = new SetterTypeResolver<T>().resolveSetter(fieldType);
//                    fieldSetter.setField(valueFromCsv, t, field);
//                }
//                parsedObjects.add(t);
//            }
//        }
//        return parsedObjects;
//    }

    private <T> void defineFieldsWithAnnotation(Class<T> tClass) {
        Field[] fields = tClass.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(CsvHeader.class)) {
                CsvHeader annotation = field.getAnnotation(CsvHeader.class);
                headersWithAnnotatedFields.put(annotation.name(), field);
            }
        }
    }

//    private void parseHeader() {
//        String headerLine = scanner.nextLine();
//        String[] headers = headerLine.split(",");
//        List<String> list = Arrays.asList(headers);
//
//        Set<String> requiredHeaders = getRequiredHeaders();
//        if (list.containsAll(requiredHeaders)) {
//            for (int i = 0; i < headers.length; i++) {
//                for (Field field : annotatedFields) {
//                    CsvHeader annotation = field.getAnnotation(CsvHeader.class);
//                    String name = annotation.name();
//                    if (name.equals(headers[i])) {
//                        headersWithIndexes.put(name, i);
//                    }
//                }
//            }
//        } else throw new RequiredValueAbsentException(REQUIRED_HEADERS_NOT_FOUND);
//    }

    private Set<String> getRequiredHeaders() {
        Set<String> requiredHeaders = new HashSet<>();
        for (Field field : headersWithAnnotatedFields.values()) {
            CsvHeader csvHeader = field.getAnnotation(CsvHeader.class);
            if (csvHeader.required()) {
                requiredHeaders.add(csvHeader.name());
            }
        }
        return requiredHeaders;
    }

    public Set<String> intersection(List<String> headersInCsv, Set<String> headersFromAnnotations) {
        Set<String> result = new HashSet<>();

        for (String header : headersInCsv) {
            if (headersFromAnnotations.contains(header)) {
                result.add(header);
            }
        }

        return result;
    }
}


