package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Node0x06 extends Node {
    private int pointer;

    public Node0x06(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        ByteBuffer data = chunk.getByteBuffer(start, 5);
        byte opcode = data.get();
        int pointer = data.getInt();

        this.pointer = pointer;
        this.tagLength = 5;
        this.dataLength = this.length + this.tagLength;
    }

    @Override
    public void parseDown() {
        if (this.pointer < this.start) {
            this.dataLength = 0;
        } else {
            NameString string = new NameString(chunk, this, pointer, this.dataLength);
            chunk.setString(this.pointer, string);
            string.parseSelf();
            string.parseDown();
            this.dataLength = string.getLength();
        }
        this.length = this.tagLength + this.dataLength;
    }

    @Override
    public String getXml() {
        Node string = this.chunk.getString(this.pointer);

        String xml = String.format(" %s", string.getXml());
        this.chunk.setDeferedOutput(xml);
        return "";
    }
}
