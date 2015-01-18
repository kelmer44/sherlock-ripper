package com.kelmer.core.fm;

public interface IFileManipulator {

    
    /**
     * Init from filePath
     * @param filePath
     */
    public void init(String filePath) throws Exception;
    
    
    /**
     * Reads a single SIGNED byte
     * @return
     */
    public byte readByte();
    
    /**
     * Reads an arbitrary number of SIGNED bytes
     * @param number
     * @return
     */
    public byte[] readByte(int number);
    
    
    /**
     * Reads a single UNSIGNED byte (thus, must return a short)
     * @return
     */
    public short readUByte();
    /**
    * Reads an arbitrary number of UNSIGNED bytes
    * @param number
    * @return
    */
    public short[] readUByte(int number);
    
    
    /**
     * Reads a single byte char as a byte
     * @return
     */
    public byte read8Char();
    
    /**
     * Reads an arbitrary number of single byte ASCII char as a byte array
     * @param number
     * @return
     */
    public byte[] read8Char(int number);
    
    /**
     * Reads an arbitrary number of single byte ASCII char as a String
     * @param number
     * @return
     */
    public String read8CharAsString(int number);
    
    
    public short read16Int();
    public short[] read16Int(int number);
    
    public int read32Int();
    public int[] read32Int(int number);
    
    public long read64Int();
    public long[] read64Int(int number);
    
    public double readDouble();
    public double[] readDouble(int number);
    
    public char read16Char();
    public char[] read16Char(int number);
    public String read16CharAsString(int number, String encoding);
    
    public void seek(long offset);
    public void rseek(long rOffset);
    public long currentPos();
    public long length();
    
}
