package com.xitaymin;

public class PrimitiveNumberFieldContainer {
    @CsvHeader(name = "byteId")
    private byte byteId;
    @CsvHeader(name = "shortId")
    private short shortId;
    @CsvHeader(name = "intId")
    private int intId;
    @CsvHeader(name = "longId")
    private long longId;
    @CsvHeader(name = "floatId")
    private float floatId;
    @CsvHeader(name = "doubleId")
    private double doubleId;

    @Override
    public String toString() {
        return "PrimitiveNumberFieldContainer{" + "byteId=" + byteId + ", shortId=" + shortId + ", intId=" + intId + ", longId=" + longId + ", floatId=" + floatId + ", doubleId=" + doubleId + '}';
    }
}
