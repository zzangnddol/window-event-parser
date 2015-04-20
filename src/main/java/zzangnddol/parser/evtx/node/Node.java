package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.type.VariantType;

public class Node {
    protected Chunk chunk;
    protected Object parent;
    protected int start;
    protected int length;

    protected int tagLength = 0;
    protected int dataLength = 0;
    //    protected BlockingQueue<Node> children = new LinkedBlockingDeque<Node>();
    protected List<Object> children = new ArrayList<Object>();
    protected int endOfStream = 0;

    protected byte flags;
    protected int depth;
    protected int next;

    protected String string = "";
    protected int tagState;
    protected short index;
    protected byte type;

    public Node(Chunk chunk, Object parent, int start, int length) {
        this.chunk = chunk;
        this.parent = parent;
        this.start = start;
        this.length = length;
    }

    private Node newSubnode(int start, int length) {
        byte opcode = chunk.getData(start, 1)[0];
        opcode = (byte) (opcode & 0x0f);
        Node child = null;
        if (opcode == 0x00) {
            child = new Node0x00(chunk, this, start, length);
        } else if (opcode == 0x01) {
            child = new Node0x01(chunk, this, start, length);
        } else if (opcode == 0x02) {
            child = new Node0x02(chunk, this, start, length);
        } else if (opcode == 0x03) {
            child = new Node0x03(chunk, this, start, length);
        } else if (opcode == 0x04) {
            child = new Node0x04(chunk, this, start, length);
        } else if (opcode == 0x05) {
            child = new Node0x05(chunk, this, start, length);
        } else if (opcode == 0x06) {
            child = new Node0x06(chunk, this, start, length);
        } else if (opcode == 0x07) {
            child = new Node0x07(chunk, this, start, length);
        } else if (opcode == 0x07) {
            child = new Node0x09(chunk, this, start, length);
        } else if (opcode == 0x09) {
            child = new Node0x00(chunk, this, start, length);
        } else if (opcode == 0x0a) {
            child = new Node0x0a(chunk, this, start, length);
        } else if (opcode == 0x0b) {
            child = new Node0x0b(chunk, this, start, length);
        } else if (opcode == 0x0c) {
            child = new Node0x0c(chunk, this, start, length);
        } else if (opcode == 0x0d) {
            child = new Node0x0d(chunk, this, start, length);
        } else if (opcode == 0x0e) {
            child = new Node0x0e(chunk, this, start, length);
        } else if (opcode == 0x0f) {
            child = new Node0x0f(chunk, this, start, length);
        }
        return child;
    }

    public Node(byte opCode) {
        this.flags = (byte) (opCode >> 4);
    }

    public void parseSelf() {
        tagLength = 0;
        dataLength = length;
    }

    public void parseDown() {
        int dataPos = start + tagLength;
        int dataLen = this.dataLength;

        while ((dataLen > 0) && this.endOfStream <= 0) {
            Node child = newSubnode(dataPos, dataLen);
            if (child != null) {
                children.add(child);
                child.parseSelf();
                child.parseDown();
                endOfStream = child.getEndOfStream();
                dataPos += child.getLength();
                dataLen -= child.getLength();
            }
        }
    }

    public String getXml() {
        String result = "";
        for (Object object : this.children) {
            if (object instanceof Node) {
                result += ((Node)object).getXml();
            } else if (object instanceof VariantType) {
                result += ((VariantType)object).getXml();
            }
        }
        return result;
    }

    protected int getEndOfStream() {
        return endOfStream;
    }

    protected int getLength() {
        return length;
    }

    /**
     * 사이즈만큼 읽어서 버린다.
     *
     * @param buffer
     * @param size
     */
    protected void trash(ByteBuffer buffer, int size) {
        for (int i = 0; i < size; i++) {
            buffer.get();
        }
    }

    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    public String getSubstitute(int index, int type, int optional) {
        Root root = this.chunk.getRoot();
        return root.getSubstitute(index, type, optional);
    }
}
