package com.kelmer.formats.archive;

import java.util.List;

import com.kelmer.core.fm.raf.BERAFFileManipulator;
import com.kelmer.formats.AnyFile;
import com.kelmer.formats.FileTypes;

public abstract class Archive extends AnyFile {

    public Archive(String path) {
        super(path);
    }

    public Archive(BERAFFileManipulator raf) {
        super(raf);
    }

    public String header;

    public int nFiles;

    public List<FileEntry> fileTable;
    
    public AnyFile getFileByIndex(int index) { return null; };
    
    public AnyFile getFileByName(String name) { return null; };
    
    public AnyFile getFileById(int id) { return null; }

    @Override
    public FileTypes getType() {
        return FileTypes.ARCHIVE;
    };
    
    
}
