package com.kelmer.formats;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.kelmer.core.fm.IFileManipulator;
import com.kelmer.core.fm.raf.LERAFFileManipulator;

public abstract class AnyFile {
    
    protected IFileManipulator fm;
    private String icon;
  
    private Path filePath;
    private String fileName;
    private String[] affectedGames;
    private long fileSize;
    
    
    
    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    private FileOpenStrategy openStrategy;

    
    public AnyFile(String path) {
        fm = new LERAFFileManipulator(path);
        filePath = Paths.get(path);
        fileName = filePath.getFileName().toString();
        setOpenStrategy(openStrategy.DIRECT);
    }

    public FileOpenStrategy getOpenStrategy() {
        return openStrategy;
    }

    public void setOpenStrategy(FileOpenStrategy openStrategy) {
        this.openStrategy = openStrategy;
    }

    public AnyFile(IFileManipulator raf) {
        this.fm = raf;
    }

    
    public AnyFile() {
        super();
    }

    public String[] getAffectedGames() {
        return affectedGames;
    }

    public void setAffectedGames(String[] affectedGames) {
        this.affectedGames = affectedGames;
    }

    
    public IFileManipulator getFm() {
        return fm;
    }

    public void setFm(IFileManipulator fm) {
        this.fm = fm;
    }

  
    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public abstract FileTypes getType();

}
