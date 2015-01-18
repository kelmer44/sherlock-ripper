package com.kelmer.core.fm.raf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import com.kelmer.core.fm.IFileManipulator;

public class BERAFFileManipulator extends RAFFileManipulator implements IFileManipulator {


    public BERAFFileManipulator(String name) {
        try {
            init(name);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void init(String filePath) throws FileNotFoundException {
        raf = new RandomAccessFile(filePath, "r");
    }

    @Override
    public void seek(long offset) {
        try {
            ((RandomAccessFile) raf).seek(offset);
        } catch (IOException e) {
            throw new ReadException("Seek failed", e);
        }
    }

    public long currentPos() {
        try {
            return ((RandomAccessFile) raf).getFilePointer();
        } catch (IOException e) {
            throw new ReadException("currentPos failed", e);
        }
    }

    @Override
    public void rseek(long rOffset) {
        try {
            ((RandomAccessFile) raf).seek(length() - rOffset);
        } catch (IOException e) {
            throw new ReadException("RSeek failed", e);
        }
    }

    @Override
    public long length() {
        try {
            return ((RandomAccessFile) raf).length();
        } catch (IOException e) {
            throw new ReadException("length failed", e);
        }
    }

}
