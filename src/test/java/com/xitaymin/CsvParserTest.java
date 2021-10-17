package com.xitaymin;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


class CsvParserTest {



    @Test
    public void checkIfAllNumberPrimitiveTypesParsed() throws IOException {
        String filePath = "src/test/resources/valid.csv";

        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        CSVParser parser = new CSVParser(Files.newBufferedReader(Paths.get(filePath)), csvFormat);
        List<CSVRecord> parsedLines = parser.getRecords();
        CSVRecord record = parsedLines.get(0);

        CsvParser csvParser = new CsvParser(filePath);
        List<TestContainer> list = csvParser.parseLines(TestContainer.class);

        Assertions.assertEquals(list.size(), 1);

        TestContainer container = list.get(0);

        Assertions.assertNotNull(container);

        Assertions.assertEquals(container.getByteId(), Byte.parseByte(record.get("byteId")));
        Assertions.assertEquals(container.getRefFloat(), Float.parseFloat(record.get("refFloat")));
        Assertions.assertEquals(container.isDone(), Boolean.parseBoolean(record.get("done")));
        Assertions.assertEquals(container.getName(), record.get("name"));
        Assertions.assertEquals(container.getSymbol(), record.get("symbol").charAt(0));
    }

    @Test
    public void checkIfExceptionThrowsWhenRequiredHeadersAbsent() {
//        String filePath = "src/test/resources/valid.csv";
//
//        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
//        CSVParser parser = new CSVParser(Files.newBufferedReader(Paths.get(filePath)), csvFormat);
//        List<CSVRecord> parsedLines = parser.getRecords();
//        CSVRecord record = parsedLines.get(0);
//
//        CsvParser csvParser = new CsvParser(filePath);
//        List<TestContainer> list = csvParser.parseLines(TestContainer.class);
//
    }

    @Test
    public void checkIfForNotRequiredHeaderDefaultValueSet() {
    }


}