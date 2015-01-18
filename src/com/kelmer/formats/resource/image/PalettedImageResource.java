package com.kelmer.formats.resource.image;

import com.kelmer.core.fm.raf.BERAFFileManipulator;

public abstract class PalettedImageResource<I, P> extends ImageResource<I> {

    
    protected P[] palette;
    
    
    public PalettedImageResource(BERAFFileManipulator raf) {
        super(raf);
    }
    
    public PalettedImageResource(String filePath) {
        super(filePath);
    }
    
}
