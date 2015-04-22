package zzangnddol.parser.evtx;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import zzangnddol.parser.evtx.node.Node;
import zzangnddol.parser.evtx.node.Root;
import zzangnddol.parser.evtx.node.Template;
import zzangnddol.parser.evtx.xml.Event;

public class Chunk {
    private static final int CHUNK_SIZE = 0x10000;      // 65536
    private static final int CHUNK_HEADER_SIZE = 0x200; // 512

    private RandomAccessFile file;
    private long chunkNumber;
    private long offset;
    private byte[] data = new byte[CHUNK_SIZE]; // 전체 데이터를 담고 있다.

    // 헤더 정보
    private long numFirst;
    private long numLast;
    private long numFirstFile;
    private long numLastFile;
    private int ofsNextRec;
    private int crc32Data;
    private int crc32Header;

    private int tagState;

    private Record currentRecord;

    private LinkedList<Node> elementStack = new LinkedList<Node>();
    private LinkedList<Root> rootStack = new LinkedList<Root>();

    private Map<Integer, Template> templateMap = new HashMap<Integer, Template>();
    private Map<Integer, Node> stringMap = new HashMap<Integer, Node>();

    public Chunk(RandomAccessFile file, long chunkNumber) throws IOException {
        this.file = file;
        this.chunkNumber = chunkNumber;

        reloadHeader();
    }

    public void reloadHeader() throws IOException {
        file.seek(FileHeader.FILE_HEADER_SIZE + chunkNumber * CHUNK_SIZE);
        file.readFully(data);

        byte[] b = getData(0, 8);
        if (!new String(b).equals("ElfChnk\000")) {
            throw new IOException("Chunk " + chunkNumber + " does not exist.");
        }

        ByteBuffer buffer = getByteBuffer(8, 16);
        numFirst = buffer.getLong();
        numLast = buffer.getLong();

        buffer = getByteBuffer(0x18, 16);
        numFirstFile = buffer.getLong();
        numLastFile = buffer.getLong();

        buffer = getByteBuffer(0x30, 4);
        ofsNextRec = buffer.getInt();

        buffer = getByteBuffer(0x34, 4);
        crc32Data = buffer.getInt();

        buffer = getByteBuffer(0x7c, 4);
        crc32Header = buffer.getInt();
    }

    public Record gotoLastRecord() throws Exception {
        Record record = firstRecord();
        Event event = record.asEvent();
        //        System.out.println("num last file: " + numLastFile);
        //        System.out.println("event record id: " + event.getSystem().getEventRecordId());
        while(event != null && numLastFile > event.getSystem().getEventRecordId()) {
            record = record.nextRecord();
            event = record.asEvent();
            //            System.out.println("event record id: " + event.getSystem().getEventRecordId());
        }
        return record;
    }

    public Event gotoLastEvent() throws Exception {
        Record record = gotoLastRecord();
        return record == null ? null : record.asEvent();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("-------- Chunk: ").append(this.chunkNumber).append("--------\n");
        buffer.append("number first     : " + this.numFirst).append("\n");
        buffer.append("number last      : " + this.numLast).append("\n");
        buffer.append("number first file: " + this.numFirstFile).append("\n");
        buffer.append("number last file : " + this.numLastFile);
        return buffer.toString();
    }

    public Record firstRecord() throws IOException {
        Record record = new Record(this, 0x200);
        this.currentRecord = record;
        return record;
    }

    public Record nextRecord() throws IOException {
        // 변경이 될 수 있기 때문에 header를 다시 읽어들인다.
        reloadHeader();
        int start = this.currentRecord.getStart() + this.currentRecord.getLength();
        if (start >= this.ofsNextRec) {
            return null;
        }

        Record record = new Record(this, start);
        this.currentRecord = record;
        return record;
    }

    public Template getTemplate(int key) {
        return templateMap.get(key);
    }

    public void setTemplate(int key, Template t) {
        this.templateMap.put(key, t);
    }

    public byte[] getData(int offset, int length) {
        byte[] tmp = new byte[length];
        java.lang.System.arraycopy(data, offset, tmp, 0, length);
        return tmp;
    }

    public ByteBuffer getByteBuffer(int offset, int length) {
        ByteBuffer buffer = ByteBuffer.wrap(getData(offset, length)).order(ByteOrder.LITTLE_ENDIAN);
        return buffer;
    }

    public void pushElement(Node node) {
        this.elementStack.add(node);
    }

    public Node popElement() {
        return this.elementStack.removeLast();
    }

    public int getDepth() {
        return this.elementStack.size();
    }

    public void setString(int address, Node node) {
        this.stringMap.put(address, node);
    }

    public Node getString(int address) {
        return this.stringMap.get(address);
    }

    public int getTagState() {
        return tagState;
    }

    public void setTagState(int tagState) {
        this.tagState = tagState;
    }

    public void pushRoot(Root node) {
        this.rootStack.addLast(node);
    }

    public Node popRoot() {
        return this.rootStack.removeLast();
    }

    public Root getRoot() {
        return this.rootStack.getLast();
    }

    private String deferedOutput = "";

    public String getDeferedOutput() {
        String xml = this.deferedOutput;
        this.deferedOutput = "";
        return xml;
    }

    public void setDeferedOutput(String xml) {
        this.deferedOutput += xml;
    }

    public Record getCurrentRecord() {
        return currentRecord;
    }

    public void setCurrentRecord(Record currentRecord) {
        this.currentRecord = currentRecord;
    }

    public int getOfsNextRec() {
        return ofsNextRec;
    }

    public void setOfsNextRec(int ofsNextRec) {
        this.ofsNextRec = ofsNextRec;
    }

    public long getChunkNumber() {
        return chunkNumber;
    }

    public long getNumLastFile() {
        return numLastFile;
    }
}
