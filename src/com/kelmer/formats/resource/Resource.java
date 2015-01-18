package com.kelmer.formats.resource;

import com.kelmer.core.fm.IFileManipulator;
import com.kelmer.formats.AnyFile;

public abstract class Resource extends AnyFile {

    public Resource(String filePath) {
        super(filePath);
    }
    
    public Resource(IFileManipulator raf) {
        super(raf);
    }

    public Resource() {
        super();
    }
    
}
