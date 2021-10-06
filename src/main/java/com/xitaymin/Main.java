package com.xitaymin;

public class Main {
    public static void main(String[] args) {
        CsvParser csvParser = new CsvParser("F:\\Hilel\\container.csv");
        csvParser.parseLines(Todo.class);
        System.out.println("Hello gradle");
    }
}
