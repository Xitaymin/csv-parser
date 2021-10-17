package com.xitaymin;

import com.xitaymin.exeptions.RequiredValueAbsentException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static com.xitaymin.CsvParser.REQUIRED_HEADERS_NOT_FOUND;
import static com.xitaymin.setters.FieldSetter.REQUIRED_FIELD_VALUE_ABSENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class CsvParserTest {

    @Test
    public void checkIfValidCsvParsedProperly() throws IOException {
        String filePath = "src/test/resources/valid.csv";

        CSVFormat csvFormat = CSVFormat.DEFAULT.withFirstRecordAsHeader();
        CSVParser parser = new CSVParser(Files.newBufferedReader(Paths.get(filePath)), csvFormat);
        List<CSVRecord> parsedLines = parser.getRecords();
        CSVRecord record = parsedLines.get(0);

        CsvParser csvParser = new CsvParser(filePath);
        List<TestContainer> list = csvParser.parseLines(TestContainer.class);

        assertEquals(list.size(), 1);

        TestContainer container = list.get(0);

        assertNotNull(container);

        assertEquals(container.getByteId(), Byte.parseByte(record.get("byteId")));
        assertEquals(container.getRefFloat(), Float.parseFloat(record.get("refFloat")));
        assertEquals(container.isDone(), Boolean.parseBoolean(record.get("done")));
        assertEquals(container.getName(), record.get("name"));
        assertEquals(container.getSymbol(), record.get("symbol").charAt(0));
    }

    @Test
    public void checkIfExceptionThrowsWhenRequiredHeadersAbsent() {
        String filePath = "src/test/resources/absent_required_field.csv";
        CsvParser csvParser = new CsvParser(filePath);

        Throwable throwable = assertThrows(RequiredValueAbsentException.class,
                () -> csvParser.parseLines(TestContainer.class));
        assertThat(throwable).hasMessage(REQUIRED_HEADERS_NOT_FOUND);
    }

    @Test
    public void checkIfExceptionThrowsWhenRequiredHeaderCantBeParsed() {
        String filePath = "src/test/resources/invalid_required_field.csv";
        CsvParser csvParser = new CsvParser(filePath);

        Throwable throwable = assertThrows(RequiredValueAbsentException.class,
                () -> csvParser.parseLines(TestContainer.class));
        assertThat(throwable).hasMessage(String.format(REQUIRED_FIELD_VALUE_ABSENT, "byteId", "byteId"));

    }

    @Test
    public void checkIfForNotRequiredHeaderDefaultValueSet() throws IOException {
        String filePath = "src/test/resources/invalid_not_required_field.csv";
        CsvParser csvParser = new CsvParser(filePath);
        List<TestContainer> list = csvParser.parseLines(TestContainer.class);
        TestContainer container = list.get(0);

        assertEquals(container.getRefFloat(), Float.MIN_VALUE);
    }


}