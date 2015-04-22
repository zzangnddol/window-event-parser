package zzangnddol.parser.evtx;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Evtx {
    private String fileName;
    private RandomAccessFile file;
    private long fileLength;
    private FileHeader fileHeader;

    private Chunk currentChunk;

    public Evtx(String fileName) throws IOException {
        init(fileName);
        readHeader();
    }

    private void init(String fileName) throws IOException {
        this.fileName = fileName;
        this.file = new RandomAccessFile(new File(fileName), "r");
        this.fileLength = this.file.length();
        this.currentChunk = null;
    }

    private void readHeader() throws IOException {
        if (this.fileLength > new File(this.fileName).length()) init(this.fileName);
        this.fileHeader = new FileHeader(readBytes(file, 0, FileHeader.FILE_HEADER_SIZE));
    }

    /**
     * 파일헤더를 다시 읽어들인다.
     *
     * @throws IOException
     */
    public void reloadHeader() throws IOException {
        readHeader();
    }

    public Chunk getChunk(long chunkNumber) throws IOException {
        return new Chunk(file, chunkNumber);
    }

    public Chunk firstChunk() throws IOException {
        this.currentChunk = getChunk(0);
        return this.currentChunk;
    }

    public Chunk nextChunk() throws IOException {
        this.currentChunk = getChunk(this.currentChunk.getChunkNumber() + 1);
        return this.currentChunk;
    }

    public Record firstRecord() throws IOException {
        this.currentChunk = firstChunk();
        return this.currentChunk.firstRecord();
    }

    public Record nextRecord() throws IOException {
        // 변경된 경우를 감지하기 위해 헤더를 다시 읽어들인다.
        reloadHeader();
        if (this.currentChunk == null) return firstRecord();
        Record record = this.currentChunk.nextRecord();
        if (record == null) {
            try {
                this.currentChunk = this.nextChunk();
                record = this.currentChunk.firstRecord();
            } catch (Exception e) {
                return null;
            }
        }
        return record;
    }

    public Record gotoLastRecord() throws IOException {
        this.currentChunk = getChunk(fileHeader.getCurrentChunkNumber());
        Record record = this.currentChunk.firstRecord();
        while (true) {
            Record next = nextRecord();
            if (next == null) break;
            record = next;
        }
        return record;
    }

    protected static byte[] readBytes(RandomAccessFile file, long offset, long length) throws IOException {
        byte[] b = new byte[(int) length];
        long current = file.getFilePointer();
        file.seek(offset);
        file.read(b);
        file.seek(current);
        return b;
    }

    public FileHeader getFileHeader() {
        return fileHeader;
    }
}
