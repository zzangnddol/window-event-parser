package zzangnddol.parser.evt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import zzangnddol.parser.util.ParseUtil;

public class Header {
    private long length;
    private String signature;   // LfLe
    private long majorVersion;
    private long minorVersion;
    private long startOffset;
    private long endOffset;
    private long currentRecordNumber;
    private long oldestRecordNumber;
    private long maxSize;
    private long flags;
    private long retention;
    private long endLength;

    public Header(byte[] bytes) {
        // 48 byte의 길이를 받아 parsing
        ByteBuffer buffer = ParseUtil.asByteBuffer(bytes, ByteOrder.LITTLE_ENDIAN);
        length = buffer.getInt();
        byte[] b = new byte[4];
        buffer.get(b);
        signature = new String(b);
        majorVersion = buffer.getInt();
        minorVersion = buffer.getInt();
        startOffset = buffer.getInt();
        endOffset = buffer.getInt();
        currentRecordNumber = buffer.getInt();
        oldestRecordNumber = buffer.getInt();
        maxSize = buffer.getInt();
        flags = buffer.getInt();
        retention = buffer.getInt();
        endLength = buffer.getInt();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("======== HEADER ========\n");
        buffer.append("length: ").append(String.format("0x%08x(%d)", length, length)).append("\n");
        buffer.append("signature: ").append(signature).append("\n");
        buffer.append("major ver: ").append(String.format("0x%08x(%d)", majorVersion, majorVersion)).append("\n");
        buffer.append("minor ver: ").append(String.format("0x%08x(%d)", minorVersion, minorVersion)).append("\n");
        buffer.append("start ofs: ").append(String.format("0x%08x(%d)", startOffset, startOffset)).append("\n");
        buffer.append("end ofs  : ").append(String.format("0x%08x(%d)", endOffset, endLength)).append("\n");
        buffer.append("cur recno: ").append(String.format("0x%08x(%d)", currentRecordNumber, currentRecordNumber)).append("\n");
        buffer.append("old recno: ").append(String.format("0x%08x(%d)", oldestRecordNumber, oldestRecordNumber)).append("\n");
        buffer.append("max size : ").append(String.format("0x%08x(%d)", maxSize, maxSize)).append("\n");
        buffer.append("flags    : ").append(String.format("0x%08x(%d)", flags, flags)).append("\n");
        buffer.append("retention: ").append(String.format("0x%08x(%d)", retention, retention)).append("\n");
        buffer.append("end len  : ").append(String.format("0x%08x(%d)", endLength, endLength));
        return buffer.toString();
    }

    public long getLength() {
        return length;
    }

    public String getSignature() {
        return signature;
    }

    public long getMajorVersion() {
        return majorVersion;
    }

    public long getMinorVersion() {
        return minorVersion;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public long getCurrentRecordNumber() {
        return currentRecordNumber;
    }

    public long getOldestRecordNumber() {
        return oldestRecordNumber;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public long getFlags() {
        return flags;
    }

    public long getRetention() {
        return retention;
    }

    public long getEndLength() {
        return endLength;
    }

}
