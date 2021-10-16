package com.xitaymin;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


class CsvParserTest {


    @Test
    public void checkIfAllNumberPrimitiveTypesParsed() throws IOException {
        CsvParser csvParser = new CsvParser("src/test/resources/test.csv");
        List<PrimitiveNumberFieldContainer> list = csvParser.parseLines(PrimitiveNumberFieldContainer.class);
        System.out.println(list);
    }
}