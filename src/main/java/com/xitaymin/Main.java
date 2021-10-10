package com.xitaymin;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException {
        CsvParser csvParser = new CsvParser("F:\\Hilel\\container.csv");
        List<Todo> list = csvParser.parseLines(Todo.class);
        System.out.println(list);
    }
}
