package zzangnddol.parser.evt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
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

    public void setLength(long length) {
        this.length = length;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public long getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(long majorVersion) {
        this.majorVersion = majorVersion;
    }

    public long getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(long minorVersion) {
        this.minorVersion = minorVersion;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
    }

    public long getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(long endOffset) {
        this.endOffset = endOffset;
    }

    public long getCurrentRecordNumber() {
        return currentRecordNumber;
    }

    public void setCurrentRecordNumber(long currentRecordNumber) {
        this.currentRecordNumber = currentRecordNumber;
    }

    public long getOldestRecordNumber() {
        return oldestRecordNumber;
    }

    public void setOldestRecordNumber(long oldestRecordNumber) {
        this.oldestRecordNumber = oldestRecordNumber;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public long getFlags() {
        return flags;
    }

    public void setFlags(long flags) {
        this.flags = flags;
    }

    public long getRetention() {
        return retention;
    }

    public void setRetention(long retention) {
        this.retention = retention;
    }

    public long getEndLength() {
        return endLength;
    }

    public void setEndLength(long endLength) {
        this.endLength = endLength;
    }
}
