package com.xitaymin;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        CsvParser csvParser = new CsvParser("src/main/resources/container.csv");
        List<Todo> list = csvParser.parseLines(Todo.class);
        System.out.println(list);
    }
}
