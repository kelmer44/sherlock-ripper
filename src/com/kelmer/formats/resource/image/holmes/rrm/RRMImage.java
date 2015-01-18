package com.kelmer.formats.resource.image.holmes.rrm;

import java.awt.image.BufferedImage;

import org.eclipse.swt.graphics.ImageData;

import com.kelmer.formats.resource.image.core.PalettedImageResource;
import com.kelmer.formats.resource.image.core.RGBPixel;
import com.kelmer.formats.resource.palette.Palette256;

public class RRMImage extends PalettedImageResource<RGBPixel, Palette256> {

    /**
     * Size is fixed
     */
    private static final int WIDTH = 320;
    private static final int HEIGHT = 138;
    private static final int BPP = 8;



    public RRMImage(String filePath) {
        super(filePath);
        this.setAffectedGames(new String[] { "The Lost files of Sherlock Holmes: the Case of the Serrated Scalpel" });
        this.width = WIDTH;
        this.height = HEIGHT;
        this.bpp = BPP;
        this.palette = new Palette256();
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
                pixels[i][j] = palette.getColor(rawPixels[j * width + i]);
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
        
        fm.rseek(width * height + Palette256.PALETTE_BYTE_SIZE);
        for (int i = 0; i < 256; i++) {
            RGBPixel pixel = new RGBPixel();
            pixel.r = fm.readUByte();
            pixel.g = fm.readUByte();
            pixel.b = fm.readUByte();
            palette.setColor(i, pixel);
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
