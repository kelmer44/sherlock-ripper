package com.kelmer.formats.resource.image.express.bg;

import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import com.kelmer.core.fm.IFileManipulator;
import com.kelmer.core.image.RGBPixel;
import com.kelmer.formats.resource.image.ImageResource;

/**
 * The Last Express BG Image format.
 * 
 * Specs by Deniz Oezmen.
 * 
 * uint32 {4} - position on screen? uint32 {4} - position on screen? uint32 {4}
 * - image width uint32 {4} - image height uint32 {4} - red colour channel data
 * size uint32 {4} - blue colour channel data size uint32 {4} - green colour
 * channel data size byte {x} - red colour channel data byte {x} - green colour
 * channel data byte {x} - blue colour channel data
 * 
 * 
 * 
 * @author kelmer
 *
 */
public class BGImage extends ImageResource<RGBPixel> {

    private static final Logger logger = Logger.getLogger(BGImage.class);

    private int screenOffsetX;
    private int screenOffsetY;

    private int rSize;
    private int gSize;
    private int bSize;

    private short[] bData;
    private short[] bOut;

    private short[] gData;
    private short[] gOut;

    private short[] rData;
    private short[] rOut;

    public BGImage(IFileManipulator raf) {
        super(raf);
        init();
    }

    public BGImage(String filePath) {
        super(filePath);
        init();
    }
    
    
    private void init() {
        this.setAffectedGames(new String[] { "The Last Express" });
        if (readHeader()) {
            this.pixels = new RGBPixel[width][height];
            readPixels();
        }
    }
   
    @Override
    public boolean readHeader() {
        logger.debug("Reading header for BG file");
        this.screenOffsetX = this.fm.read32Int();
        this.screenOffsetY = this.fm.read32Int();
        this.width = this.fm.read32Int();
        this.height = this.fm.read32Int();
        this.rSize = this.fm.read32Int();
        this.bSize = this.fm.read32Int();
        this.gSize = this.fm.read32Int();
        return true;
    }

    @Override
    public void readPixels() {
        logger.debug("Reading image with " + this.width + " by " + this.height + " pixels");
        rData = new short[rSize];
        gData = new short[gSize];
        bData = new short[bSize];

        rData = fm.readUByte(rSize);
        bData = fm.readUByte(bSize);
        gData = fm.readUByte(gSize);

        rOut = new short[width * height];
        gOut = new short[width * height];
        bOut = new short[width * height];

        int numPixels = width * height;
        rOut = decompress(rData, rSize, numPixels);
        gOut = decompress(gData, gSize, numPixels);
        bOut = decompress(bData, bSize, numPixels);

        int pixel = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixel = i * width + j;
                this.pixels[j][i] = new RGBPixel(rOut[pixel], gOut[pixel], bOut[pixel]);
            }
        }
    }

    private short[] decompress(short[] input, int inSize, int outSize) {
        short[] outBytes = new short[outSize];
        
        int inPos = 0;
        int outPos = 0;

        while (inPos < inSize) {
            short inByte = input[inPos++];
            if (inByte < 0x80) {
                short len = (short) ((inByte >> 5) + 1);
                short data = (short) (inByte & 0x1f);
                for (int i = 0; i < len && outPos < outSize; i++) {
                    outBytes[outPos++] = data;
                }
            } else {
                // Buffer back reference, 4096 byte window
                // Take inByte and the following value as a big endian
                // OfsLen while zeroing the first bit
                short ofsLen = (short) (((inByte & 0x7F) << 8) | input[inPos++]);
                int len = (ofsLen >> 12) + 3;
                int hisPos = (int) (outPos + (ofsLen & 0x0FFF) - 4096);
                for (int i = 0; i < len && outPos < outSize; i++)
                    outBytes[outPos++] = outBytes[hisPos++];
            }
        }
        return outBytes;
    }
    
    
    
    @Override
    public BufferedImage toBitmap() {
        BufferedImage b = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                /**
                 * Background: The VGA palette supported up to 18 bits per
                 * color, i.e. 6 bits per channel. Multiplying with 4 is a fast
                 * way to scale the channels approximately from 6 to 8 bits.
                 * (The method is a bit inaccurate, however, since the resulting
                 * colors are ever so slightly too dark: For example, a VGA
                 * white (63, 63, 63) multiplied by 4 results only in "almost"
                 * white (252, 252, 252) instead of (255, 255, 255). If you
                 * wanted precise results, you would have to do the proper
                 * linear scaling by 255/63.)
                 */
                int red = pixels[i][j].r * 4; // read from array
                int green = pixels[i][j].g * 4; // read from array
                int blue = pixels[i][j].b * 4; // read from array
                b.setRGB(i, j, RGBPixel.toRGBInteger(red, green, blue));
            }
        return b;
    }

}
