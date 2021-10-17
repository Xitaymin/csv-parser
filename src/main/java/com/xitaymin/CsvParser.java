package com.xitaymin;

import com.xitaymin.exeptions.BaseApplicationException;
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
    public static final String REQUIRED_HEADERS_NOT_FOUND = "Csv file doesn't contains all required field headers.";
    private final Reader reader;
    private final Map<String, Field> headersWithAnnotatedFields = new HashMap<>();

    public CsvParser(String filePath) {
        File csvContainer = new File(filePath);
        if (isFileAvailable(csvContainer)) {
            try {
                reader = Files.newBufferedReader(Paths.get(filePath));
            } catch (IOException e) {
                throw new BaseApplicationException(e);
            }
        } else throw new CsvContainerNotAvailableException(String.format(FILE_NOT_AVAILABLE, filePath));
    }

    private static boolean isFileAvailable(File file) {
        return file.exists() && file.canRead();
    }

    public <T> List<T> parseLines(Class<T> tClass) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        CSVParser parser = new CSVParser(reader, csvFormat);
        List<String> headersInCsv = parser.getHeaderNames();

        defineFieldsWithAnnotation(tClass);
        //todo define annotated fields and required headers
        if (headersInCsv.containsAll(getRequiredHeaders())) {
            List<T> parsedObjects = new ArrayList<>();
            Set<String> headersFromAnnotationInCsv = intersection(headersInCsv, headersWithAnnotatedFields.keySet());
            for (CSVRecord record : parser.getRecords()) {

                T parsedObject;
                try {
                    parsedObject = tClass.getDeclaredConstructor().newInstance();
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    throw new BaseApplicationException(e);
                }

                for (String header : headersFromAnnotationInCsv) {

                    Field field = headersWithAnnotatedFields.get(header);
                    field.setAccessible(true);
                    String fieldType = field.getType().getSimpleName();

                    FieldSetter<T> fieldSetter = new SetterTypeResolver<T>().resolveSetter(fieldType);
                    try {
                        fieldSetter.setField(record.get(header), parsedObject, field);
                    } catch (IllegalAccessException e) {
                        throw new BaseApplicationException(e);
                    }
                }

                parsedObjects.add(parsedObject);
            }
            return parsedObjects;
        } else throw new RequiredValueAbsentException(REQUIRED_HEADERS_NOT_FOUND);
    }


    //todo optimize this

    private <T> void defineFieldsWithAnnotation(Class<T> tClass) {
        Field[] fields = tClass.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(CsvHeader.class)) {
                CsvHeader annotation = field.getAnnotation(CsvHeader.class);
                headersWithAnnotatedFields.put(annotation.name(), field);
            }
        }
    }

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


