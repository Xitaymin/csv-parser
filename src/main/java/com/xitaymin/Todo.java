package com.xitaymin;

public class Todo {
    @CsvHeader(name = "id")
    private int id;
    @CsvHeader(name = "text")
    private String name;
    @CsvHeader(name = "done", required = false)
    private boolean done;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", required=" + done +
                '}';
    }
}
