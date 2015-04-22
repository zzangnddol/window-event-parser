package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Node0x09 extends Node {
    private int pointer;

    public Node0x09(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        ByteBuffer data = chunk.getByteBuffer(start, 5);
        byte opcode = data.get();
        int pointer = data.getInt();

        this.tagLength = 5;
        this.dataLength = this.length -5;
        this.flags = (byte) (opcode >> 4);
        this.pointer = pointer;
    }

    @Override
    public void parseDown() {
        if (this.pointer < this.start) {
            this.dataLength = 0;
        } else {
            NameString string = new NameString(chunk, this, this.pointer, this.dataLength);
            chunk.setString(this.pointer, string);
            string.parseSelf();
            string.parseDown();

            this.dataLength = string.getLength();
        }
        this.length = this.tagLength + this.dataLength;
    }

    @Override
    public String getXml() {
        Node node = this.chunk.getString(this.pointer);
        String xml = String.format("&%s", node.getXml());
        return xml;
    }
}
