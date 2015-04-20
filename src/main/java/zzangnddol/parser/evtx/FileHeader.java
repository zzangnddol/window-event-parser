package zzangnddol.parser.evtx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class FileHeader {
    public static final int FILE_HEADER_SIZE = 0x1000; // 4096

    private String magic;
    private long oldestChunk;
    private long currentChunkNumber;
    private long nextRecordNumber;
    private int headerSize;
    private short minorVersion;
    private short majorVersion;
    private short headerChunkSize;
    private short chunkCount;
    private byte[] unused1;
    private int flags;
    private int checksum;

    public FileHeader(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        magic = new String(bytes, 0, 8).trim();
        buffer.position(8);
        oldestChunk = buffer.getLong();
        currentChunkNumber = buffer.getLong();
        nextRecordNumber = buffer.getLong();
        headerSize = buffer.getInt();
        minorVersion = buffer.getShort();
        majorVersion = buffer.getShort();
        headerChunkSize = buffer.getShort();
        chunkCount = buffer.getShort();
        buffer.position(120);
        flags = buffer.getInt();
        checksum = buffer.getInt();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("--------- FILE HEADER ------------").append("\n");
        buffer.append("magic             : " + magic).append("\n");
        buffer.append("oldestChunk       : " + oldestChunk).append("\n");
        buffer.append("currentChunkNumber: " + currentChunkNumber).append("\n");
        buffer.append("nextRecordNumber  : " + nextRecordNumber).append("\n");
        buffer.append("headerSize        : " + headerSize).append("\n");
        buffer.append("minorVersion      : " + minorVersion).append("\n");
        buffer.append("majorVersion      : " + majorVersion).append("\n");
        buffer.append("headerChunkSize   : " + headerChunkSize).append("\n");
        buffer.append("chunkCount        : " + chunkCount).append("\n");
        buffer.append("flags             : " + flags).append("\n");
        buffer.append("checksum          : " + checksum);
        return buffer.toString();
    }

    public long getCurrentChunkNumber() {
        return currentChunkNumber;
    }

    public long getNextRecordNumber() {
        return nextRecordNumber;
    }
}
