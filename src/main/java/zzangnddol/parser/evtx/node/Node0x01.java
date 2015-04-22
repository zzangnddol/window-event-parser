package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Node0x01 extends Node {
    private int pointer;

    private int elementType;

    public Node0x01(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public String getXml() {
        Node string = this.chunk.getString(this.pointer);
        String xml = "\n<";
        xml += string.getXml();

        String tmp = super.getXml();
        if (tmp.indexOf("=") >= 0 && !tmp.startsWith(" ")) {
            xml += " " + tmp;
        } else {
            xml += tmp;
        }

        if (this.elementType == 0) {
            xml += " />";
        } else if (this.elementType == 1) {
            xml += String.format("</%s>", string.getXml());
        }
        return xml;
    }

    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, 11);
        byte opcode = buffer.get();
        short unknown = buffer.getShort();
        int length = buffer.getInt();
        int pointer = buffer.getInt();

        this.tagLength = 11;
        this.length = length + 6;
        this.dataLength = this.length - 11;
        this.flags = (byte) (opcode >> 4);
        this.pointer = pointer;

        chunk.setTagState(1);

        chunk.pushElement(this);
        depth = chunk.getDepth();
    }

    @Override
    public void parseDown() {
        Node string = null;
        if (pointer < start) {
            if ((flags & 4) != 0) {
                tagLength += 4;
            }
        } else {
            string = new NameString(chunk, this, pointer, dataLength);
            chunk.setString(pointer, string);
            string.parseSelf();
            string.parseDown();
            if ((this.flags & 4) == 4) {
                this.tagLength += string.getLength() + 4;
            } else {
                this.tagLength += string.getLength();
            }
        }
        this.dataLength = this.length - this.tagLength;
        super.parseDown();
    }

    public void setElementType(int type) {
        this.elementType = type;
    }

}
