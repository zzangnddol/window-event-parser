package zzangnddol.parser.evt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Cursor {
    private long length;
    private long signature1;    // 0x11111111
    private long signature2;    // 0x22222222
    private long signature3;    // 0x33333333
    private long signature4;    // 0x44444444
    private long startOffset;
    private long nextOffset;
    private long currentRecordNumber;
    private long oldestRecordNumber;

    @SuppressWarnings("unused")
    public Cursor(byte[] bytes) {
        // 40byte의 cursor record를 받아 처리한다.
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        length = buffer.getInt();
        signature1 = buffer.getInt();
        signature2 = buffer.getInt();
        signature3 = buffer.getInt();
        signature4 = buffer.getInt();
        startOffset = buffer.getInt();
        nextOffset = buffer.getInt();
        currentRecordNumber = buffer.getInt();
        oldestRecordNumber = buffer.getInt();
        long length2 = buffer.getInt();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("======== CURSOR(EOF) ========\n");
        buffer.append("length   : ").append(String.format("0x%08x(%d)", length, length)).append("\n");
        buffer.append("sig 1    : ").append(String.format("0x%08x", signature1)).append("\n");
        buffer.append("sig 2    : ").append(String.format("0x%08x", signature2)).append("\n");
        buffer.append("sig 3    : ").append(String.format("0x%08x", signature3)).append("\n");
        buffer.append("sig 4    : ").append(String.format("0x%08x", signature4)).append("\n");
        buffer.append("start off: ").append(String.format("0x%08x(%d)", startOffset, startOffset)).append("\n");
        buffer.append("next off : ").append(String.format("0x%08x(%d)", nextOffset, nextOffset)).append("\n");
        buffer.append("cur recno: ").append(String.format("0x%08x(%d)", currentRecordNumber, currentRecordNumber)).append("\n");
        buffer.append("old recno: ").append(String.format("0x%08x(%d)", oldestRecordNumber, oldestRecordNumber));
        return buffer.toString();
    }

    public long getLength() {
        return length;
    }

    public long getSignautre1() {
        return signature1;
    }

    public long getSignature2() {
        return signature2;
    }

    public long getStartOffset() {
        return startOffset;
    }

    public long getNextOffset() {
        return nextOffset;
    }

    public long getCurrentRecordNumber() {
        return currentRecordNumber;
    }

    public long getOldestRecordNumber() {
        return oldestRecordNumber;
    }

    public long getSignature3() {
        return signature3;
    }

    public long getSignature4() {
        return signature4;
    }

}
