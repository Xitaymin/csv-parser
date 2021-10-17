package com.xitaymin;

public class Todo {
    @CsvHeader(name = "id", required = false)
    private int id;
    @CsvHeader(name = "text", required = false)
    private String name;
    @CsvHeader(name = "done", required = false)
    private boolean done;

    @Override
    public String toString() {
        return "Todo{" + "id=" + id + ", name='" + name + '\'' + ", done=" + done + '}';
    }
}
