package com.xitaymin;

public class TestContainer {
    @CsvHeader(name = "byteId")
    private byte byteId;
    @CsvHeader(name = "refFloat")
    private Float refFloat;
    @CsvHeader(name = "done")
    private boolean done;
    @CsvHeader(name = "name")
    private String name;
    @CsvHeader(name = "symbol")
    private Character symbol;

    public byte getByteId() {
        return byteId;
    }

    public void setByteId(byte byteId) {
        this.byteId = byteId;
    }

    public Float getRefFloat() {
        return refFloat;
    }

    public void setRefFloat(Float refFloat) {
        this.refFloat = refFloat;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getSymbol() {
        return symbol;
    }

    public void setSymbol(Character symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "TestContainer{" + "byteId=" + byteId + ", refFloat=" + refFloat + ", done=" + done + ", name='" + name + '\'' + ", symbol=" + symbol + '}';
    }
}
