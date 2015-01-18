package com.kelmer.formats.resource.image.holmes.rrm;

import java.awt.image.BufferedImage;

import org.eclipse.swt.graphics.ImageData;

import com.kelmer.core.image.RGBPixel;
import com.kelmer.formats.resource.image.PalettedImageResource;

public class RRMImage extends PalettedImageResource<RGBPixel, RGBPixel> {

    private static final int PALETTE_SIZE = 256;
    /**
     * Size is fixed
     */
    private static final int WIDTH = 320;
    private static final int HEIGHT = 138;
    private static final int BPP = 8;

    private int paletteSize = PALETTE_SIZE * 3;
    /**
     * Since Java cannot hold unsigned byte, we must use short...
     */
    public short[] rawPixels;

    public RRMImage(String filePath) {
        super(filePath);
        this.setAffectedGames(new String[] { "The Lost files of Sherlock Holmes: the Case of the Serrated Scalpel" });
        this.width = WIDTH;
        this.height = HEIGHT;
        this.bpp = BPP;
        this.palette = new RGBPixel[PALETTE_SIZE];
        this.rawPixels = new short[width * height];
        this.pixels = new RGBPixel[width][height];

        readPalette();
        readPixels();
    }

    /**
     * bytes in image data contain indexes of palette for each pixels.
     */
    public void readPixels() {
        rawPixels = fm.readUByte(rawPixels.length);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = palette[rawPixels[j * width + i]];
            }
        }
    }

    /**
     * Reads palette for RRM file.
     * 
     * From end position of file, we go back the size of the screen, then the
     * palette size.
     *
     */
    private void readPalette() {
        
        fm.rseek(width * height + paletteSize);
        for (int i = 0; i < 256; i++) {
            palette[i] = new RGBPixel();
            palette[i].r = fm.readUByte();
            palette[i].g = fm.readUByte();
            palette[i].b = fm.readUByte();
        }
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

    @Override
    public boolean readHeader() {
       //No Op
        return true;
    }

}
