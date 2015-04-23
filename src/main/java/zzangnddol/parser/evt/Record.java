package zzangnddol.parser.evt;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Date;

import zzangnddol.parser.evtx.EvtxUtil;
import zzangnddol.parser.util.ParseUtil;

public class Record {
    private long offset;

    private long length;
    private String signature;   // LfLe
    private long recordNumber;
    private Date timeGenerated;
    private Date timeWritten;
    private int eventId;
    private int eventType;
    private int numStrings;
    private int eventCategory;
    private int reservedFlags;
    private long closingRecordNumber;
    private long stringsOffset;
    private long userSidLength;
    private long userSidOffset;
    private long dataLength;
    private long dataOffset;

    // 해당 레코드의 전체 byte
    private byte[] bytes;

    @SuppressWarnings("unused")
    private String sid = "";
    private String source = "";
    private String strings = "";

    /**
     * Make record from bytes
     *
     * @param offset    offset at file
     * @param bytes     record data
     */
    public Record(long offset, byte[] bytes) {
        this.offset = offset;
        this.bytes = bytes;
        ByteBuffer buffer = ParseUtil.asByteBuffer(bytes, ByteOrder.LITTLE_ENDIAN);
        length = buffer.getInt();
        // magic
        byte[] b = new byte[4];
        buffer.get(b);
        signature = new String(b);
        recordNumber = buffer.getInt();
        timeGenerated = secondsToDate(buffer.getInt());
        timeWritten = secondsToDate(buffer.getInt());
        eventId = buffer.getShort();
        buffer.getShort();  // 2 byte 버림
        eventType = buffer.getShort();  // offset start at 0x18
        numStrings = buffer.getShort();
        eventCategory = buffer.getShort();
        reservedFlags = buffer.getShort();
        closingRecordNumber = buffer.getInt();
        stringsOffset = buffer.getInt();
        userSidLength = buffer.getInt();
        userSidOffset = buffer.getInt();
        dataLength = buffer.getInt();
        dataOffset = buffer.getInt();
        if (userSidLength > 0) {
            makeSid();
        }
        if (dataLength > 0) {
            makeData();
        }
        makeSource();
        makeStrings();
    }

    public String fullString() {
        return String.format("EVT[%s] %d %s %s",
                this.source,
                this.eventId,
                EvtxUtil.toGMTTimeString(timeGenerated),
                this.strings);
    }

    public long nextRecordOffset() {
        return this.offset + this.length;
    }

    private void makeData() {

    }

    private void makeStrings() {
        ByteBuffer buffer = ByteBuffer.wrap(bytes, (int) stringsOffset, (int) (bytes.length - this.stringsOffset - 4));
        CharsetDecoder decoder = Charset.forName("UTF-16LE").newDecoder();
        try {
            String s = decoder.decode(buffer).toString();
            String[] tokens = s.split("\0");
            StringBuffer tmp = new StringBuffer();
            for (String token : tokens) {
                if (tmp.length() > 0) tmp.append(",");
                tmp.append(token.trim());
            }
            this.strings = "[" + tmp.toString() + "]";
            this.strings = this.strings.replaceAll("\\r", "");
            this.strings = this.strings.replaceAll("\\n", ";");
            this.strings = this.strings.replaceAll("\\t", "");
        } catch (CharacterCodingException e) {
            this.strings = "";
        }
    }

    private void makeSource() {
        ByteBuffer buffer = ByteBuffer.wrap(this.bytes, 0x38, this.bytes.length - 0x38);
        CharsetDecoder decoder = Charset.forName("UTF-16LE").newDecoder();
        try {
            this.source = decoder.decode(buffer).toString();
            this.source = this.source.split("\0")[0];
            this.source = this.source.trim();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
    }

    private void makeSid() {
        StringBuffer ret = new StringBuffer();
        ByteBuffer buffer = ByteBuffer.wrap(bytes, (int)userSidOffset, (int)(bytes.length - userSidOffset));
        byte revision = buffer.get();
        byte subAuthorityCount = buffer.get();
        short highPart = buffer.getShort();
        int lowPart = buffer.getInt();
        long identifierAuthority = ((long)highPart) << 32 + lowPart;
        ret.append(String.format("S-%d-%d", revision, identifierAuthority));
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < subAuthorityCount; i++) {
            ret.append(String.format("-%d", buffer.getInt()));
        }
        this.sid = ret.toString();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("======== RECORD ========\n");
        buffer.append("offset   : ").append(String.format("0x%08x(%d)", offset, offset)).append("\n");
        buffer.append("length   : ").append(String.format("0x%08x(%d)", length, length)).append("\n");
        buffer.append("signature: ").append(signature).append("\n");
        buffer.append("rec no   : ").append(String.format("0x%08x(%d)", recordNumber, recordNumber)).append("\n");
        buffer.append("time-gen : ").append(timeGenerated).append("\n");
        buffer.append("time-writ: ").append(timeWritten).append("\n");
        buffer.append("event id : ").append(String.format("0x%08x(%d)", eventId, eventId)).append("\n");
        buffer.append("event typ: ").append(String.format("0x%08x(%d)", eventType, eventType)).append("\n");
        buffer.append("num str  : ").append(String.format("0x%08x(%d)", numStrings, numStrings)).append("\n");
        buffer.append("event cat: ").append(String.format("0x%08x(%d)", eventCategory, eventCategory)).append("\n");
        buffer.append("resv flag: ").append(String.format("0x%08x(%d)", reservedFlags, reservedFlags)).append("\n");
        buffer.append("clo recno: ").append(String.format("0x%08x(%d)", closingRecordNumber, closingRecordNumber)).append("\n");
        buffer.append("str ofs  : ").append(String.format("0x%08x(%d)", stringsOffset, stringsOffset)).append("\n");
        buffer.append("usr-sidln: ").append(String.format("0x%08x(%d)", userSidLength, userSidLength)).append("\n");
        buffer.append("usr-sidof: ").append(String.format("0x%08x(%d)", userSidOffset, userSidOffset)).append("\n");
        buffer.append("data len : ").append(String.format("0x%08x(%d)", dataLength, dataLength)).append("\n");
        buffer.append("data ofs : ").append(String.format("0x%08x(%d)", dataOffset, dataOffset));
        return buffer.toString();
    }

    private Date secondsToDate(long seconds) {
        return new Date(seconds * 1000L);
    }

    public long getLength() {
        return length;
    }

    public String getSignature() {
        return signature;
    }

    public long getRecordNumber() {
        return recordNumber;
    }

    public Date getTimeGenerated() {
        return timeGenerated;
    }

    public Date getTimeWritten() {
        return timeWritten;
    }

    public int getEventId() {
        return eventId;
    }

    public int getEventType() {
        return eventType;
    }

    public int getNumStrings() {
        return numStrings;
    }

    public int getEventCategory() {
        return eventCategory;
    }

    public int getReservedFlags() {
        return reservedFlags;
    }

    public long getClosingRecordNumber() {
        return closingRecordNumber;
    }

    public long getStringsOffset() {
        return stringsOffset;
    }

    public long getUserSidLength() {
        return userSidLength;
    }

    public long getUserSidOffset() {
        return userSidOffset;
    }

    public long getDataLength() {
        return dataLength;
    }

    public long getDataOffset() {
        return dataOffset;
    }

}
