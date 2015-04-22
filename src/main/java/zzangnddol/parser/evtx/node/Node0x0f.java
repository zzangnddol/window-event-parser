package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Node0x0f extends Node {

    public Node0x0f(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        ByteBuffer data = chunk.getByteBuffer(start, 4);
        byte opcode = data.get();
        byte unknown1 = data.get();
        short unknown2 = data.getShort();

        this.length = 4;
        this.tagLength = 4;
        this.dataLength = 0;
    }

    @Override
    public void parseDown() {

    }

    @Override
    public String getXml() {
        return "";
    }
}
