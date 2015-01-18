package com.kelmer.formats.archive.lib;

import java.util.ArrayList;

import com.kelmer.formats.AnyFile;
import com.kelmer.formats.archive.Archive;
import com.kelmer.formats.archive.FileEntry;

/**
 * Sherlock Holmes Serrated Scalpel Lib explorer
 * 
 * @author kelmer
 *
 */
public class LibArchive extends Archive {

    private static final int HEADER_STR_SIZE = 3;

    public LibArchive(String path) {
        super(path);
        if (checkHeader()) {
            readFileTable();
        }
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getnFiles() {
        return nFiles;
    }

    public void setnFiles(int nFiles) {
        this.nFiles = nFiles;
    }

    private void readFileTable() {

        this.fileTable = new ArrayList<FileEntry>(nFiles);

        byte dummy = 0;
        for (int i = 0; i < nFiles; i++) {
            FileEntry fileEntry = new FileEntry();
            fileEntry.id = i;
            dummy = fm.readByte();
            fileEntry.fileName = fm.read8CharAsString(12);
            dummy = fm.readByte();

            // offset is stored in 3-byte integer
            byte[] offset = fm.readByte(3);
            fileEntry.offset = offset[0] + offset[1] * 256 + offset[2] * 65536;
            fileTable.add(fileEntry);
        }

    }

    protected void readHeader() {

        header = getFm().read8CharAsString(3);
        int dummy = getFm().readByte();
        nFiles = getFm().readByte();
    }

    protected boolean checkHeader() {

        if (this.getFm().length() > 6) {
            readHeader();
            if (header.equals("LIB")) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AnyFile getFileByIndex(int index) {

        if (index < this.nFiles) {
            FileEntry f = this.fileTable.get(index);
            fm.seek(f.offset);
            long startPos = f.offset;

            long endPos = 0;

            if (index < this.nFiles - 1) {
                FileEntry next = fileTable.get(index+1);
                endPos = next.offset; 
            }
            else {
                fm.length();
            }
            
            return null;
        } else {
            return null;
        }
    }

    @Override
    public AnyFile getFileById(int id) {
        return getFileByIndex(id);
    }

}
