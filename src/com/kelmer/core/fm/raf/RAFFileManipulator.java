package com.kelmer.core.fm.raf;

import java.io.DataInput;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import com.kelmer.core.fm.IFileManipulator;

public abstract class RAFFileManipulator implements IFileManipulator {

    public DataInput raf;

    @Override
    public byte readByte() {
        try {
            return raf.readByte();
        } catch (IOException e) {
            throw new ReadException("readByte failed", e);
        }
    }

    @Override
    public byte[] readByte(int number) {
        byte[] sArray = new byte[number];
        for (int i = 0; i < number; i++) {
            sArray[i] = readByte();
        }
        return sArray;
    }

    @Override
    public byte read8Char() {
        return readByte();
    }

    @Override
    public byte[] read8Char(int number) {
        return readByte(number);
    }

    @Override
    public String read8CharAsString(int number) {
        try {
            return new String(readByte(number), StandardCharsets.US_ASCII.name());
        } catch (UnsupportedEncodingException e) {

            throw new ReadException("Conversion to String failed", e);
        }
    }

    @Override
    public short read16Int() {
        try {
            return raf.readShort();
        } catch (IOException e) {
            throw new ReadException("read16int failed", e);
        }
    }

    @Override
    public short[] read16Int(int number) {
        short[] sArray = new short[number];
        for (int i = 0; i < number; i++) {
            sArray[i] = read16Int();
        }
        return sArray;
    }

    @Override
    public int read32Int() {
        try {
            return raf.readInt();
        } catch (IOException e) {
            throw new ReadException("read32Int failed", e);
        }
    }

    @Override
    public int[] read32Int(int number) {
        int[] sArray = new int[number];
        for (int i = 0; i < number; i++) {
            sArray[i] = read32Int();
        }
        return sArray;
    }

    @Override
    public long read64Int() {
        try {
            return raf.readLong();
        } catch (IOException e) {
            throw new ReadException("read64Int failed", e);
        }
    }

    @Override
    public long[] read64Int(int number) {
        long[] sArray = new long[number];
        for (int i = 0; i < number; i++) {
            sArray[i] = read64Int();
        }
        return sArray;
    }

    @Override
    public double readDouble() {
        try {
            return raf.readDouble();
        } catch (IOException e) {
            throw new ReadException("readDouble failed", e);
        }
    }

    @Override
    public double[] readDouble(int number) {
        double[] sArray = new double[number];
        for (int i = 0; i < number; i++) {
            sArray[i] = readDouble();
        }
        return sArray;
    }

    @Override
    public char read16Char() {
        try {
            return raf.readChar();
        } catch (IOException e) {
            throw new ReadException("read16Char failed", e);
        }
    }

    @Override
    public char[] read16Char(int number) {
        char[] sArray = new char[number];
        for (int i = 0; i < number; i++) {
            sArray[i] = read16Char();
        }
        return sArray;
    }

    @Override
    public String read16CharAsString(int number, String encoding) {
        try {
            return new String(readByte(number), encoding);
        } catch (UnsupportedEncodingException e) {
            throw new ReadException("Conversion to String failed", e);
        }
    }


    @Override
    public short readUByte() {
        try {
            return (short) raf.readUnsignedByte();
        } catch (IOException e) {
            throw new ReadException("readUByte failed", e);
        }
    }

    @Override
    public short[] readUByte(int number) {
        short[] sArray = new short[number];
        for (int i = 0; i < number; i++) {
            sArray[i] = readUByte();
        }
        return sArray;
    }

}
