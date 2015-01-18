package com.kelmer.core.image;

public class RGBPixel {
    
    public int r;
    public int g;
    public int b;

    public RGBPixel() 
    {
        
    }
    
    
    public RGBPixel(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    
    public RGBPixel fromInteger(int rgb){
        return null;
    }
    
    public static int toRGBInteger(int r, int g, int b)
    {
        return ((r&0x0ff)<<16)|((g&0x0ff)<<8)|(b&0x0ff);
    }

    @Override
    public String toString() {
        return "RGBPixel [" + r + ", " + g + ", " + b + "]";
    }
    
    
}
