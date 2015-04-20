package zzangnddol.parser.evtx;

import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import zzangnddol.parser.evtx.node.Node;
import zzangnddol.parser.evtx.node.Root;
import zzangnddol.parser.evtx.xml.Event;

public class Record {
    private Chunk chunk;
    private int start;

    private int magic;
    private int length;
    private long recordId;
    private Date timeCreated;

    private Root root;

    private BlockingQueue<Node> children = new LinkedBlockingDeque<Node>();

    public Record(Chunk chunk, int start) {
        this.chunk = chunk;
        this.start = start;

        ByteBuffer buffer = chunk.getByteBuffer(start, 4);
        magic = buffer.getInt();

        buffer = chunk.getByteBuffer(start + 4, 20);
        length = buffer.getInt();
        recordId = buffer.getLong();
        timeCreated = new Date((buffer.getLong() / 10000L - 11644473600000L));

        buffer = chunk.getByteBuffer(start + length - 4, 4);
        int length2 = buffer.getInt();
        assert length == length2 : "Invalid length";

        root = new Root(chunk, this, start + 24, length -28);
        root.parseSelf();
        root.parseDown();
    }

    public String getXml() {
        return this.root.getXml();
    }

    public Record nextRecord() throws IOException {
        return this.chunk.nextRecord();
    }

    public Event asEvent() throws Exception {
        JAXBContext context = JAXBContext.newInstance(Event.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        InputSource is = new InputSource(new StringReader(getXml()));
        SAXParserFactory sax = SAXParserFactory.newInstance();
        sax.setNamespaceAware(false);

        XMLReader reader = sax.newSAXParser().getXMLReader();
        SAXSource source = new SAXSource(reader, is);

        Event e = (Event) unmarshaller.unmarshal(source);
        e.setRecord(this);
        return e;
    }

    public void content() throws IOException {
        //        byte[] bytes = Evtx.readBytes(file, offset + 0x18, size - 0x18 - 4);
        //        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        //        System.out.println(buffer.remaining());
        //        while(buffer.hasRemaining()) {
        //            Node node = null;
        //            int position = buffer.position();
        //            byte b = (byte) (buffer.get() & 0x0f);
        //            System.out.println((position + 536) + "--> " + hex(b));
        //            switch(b) {
        //            case 0x00:
        //                node = new Node0x00(b, buffer);
        //                break;
        //            case 0x01:
        //                node = new Node0x01(b, buffer);
        //                break;
        //            case 0x02:
        //                node = new Node0x02(b, buffer);
        //                break;
        //            case 0x03:
        //                node = new Node0x03(b, buffer);
        //                break;
        //            case 0x04:
        //                node = new Node0x04(b, buffer);
        //                break;
        //            case 0x05:
        //                node = new Node0x05(b, buffer);
        //                break;
        //            case 0x06:
        //                node = new Node0x06(b, buffer);
        //                break;
        //            case 0x07:
        //                node = new Node0x07(b, buffer);
        //                break;
        //            case 0x09:
        //                node = new Node0x09(b, buffer);
        //                break;
        //            case 0x0a:
        //                node = new Node0x0a(b, buffer);
        //                break;
        //            case 0x0b:
        //                node = new Node0x0b(b, buffer);
        //                break;
        //            case 0x0c:
        //                node = new Node0x0c(b, buffer);
        //                break;
        //            case 0x0d:
        //                node = new Node0x0d(b, buffer);
        //                break;
        //            case 0x0e:
        //                node = new Node0x0e(b, buffer);
        //                break;
        //            case 0x0f:
        //                node = new Node0x0f(b, buffer);
        //                break;
        //            }
        //            if (node != null) {
        //                node.setChunk(chunk);
        //                children.add(node);
        //            }
        //        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        //        buffer.append("--------- Record ------------").append("\n");
        //        buffer.append("magic: " + magic).append("\n");
        //        buffer.append("size: " + size).append("\n");
        //        buffer.append("recordNum: " + recordNum).append("\n");
        //        buffer.append("timestamp: " + timestamp.toGMTString());
        return buffer.toString();
    }

    private String hex(byte b) {
        return HexDump.toHexString(b);
    }

    private String hex(int i) {
        return HexDump.toHexString(i);
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
