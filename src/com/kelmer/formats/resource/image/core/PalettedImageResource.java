package com.kelmer.formats.resource.image.core;

import com.kelmer.core.fm.raf.BERAFFileManipulator;
import com.kelmer.formats.resource.palette.Palette;

public abstract class PalettedImageResource<I, P extends Palette> extends ImageResource<I> {

    /**
     * Palette (generally 256 color)
     */
    protected P palette;
    /**
     * Since Java cannot hold unsigned byte, we must use short...
     * 
     * In paletted resources image data are single byte items representing
     * colors in the palette (which may or may not be external)
     */
    protected short[] rawPixels;

    public PalettedImageResource(BERAFFileManipulator raf) {
        super(raf);
    }

    public PalettedImageResource(String filePath) {
        super(filePath);
    }
    
    

}
