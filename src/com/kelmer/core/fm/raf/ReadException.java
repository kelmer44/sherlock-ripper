package com.kelmer.core.fm.raf;

public class ReadException extends RuntimeException {

    public ReadException(String message) {
        super(message);
    }
    
    public ReadException(String message, Throwable t) {
        super(message, t);
    }
}
