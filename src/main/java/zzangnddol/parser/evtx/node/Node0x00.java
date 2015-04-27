package zzangnddol.parser.evtx.node;

import zzangnddol.parser.evtx.Chunk;

public class Node0x00 extends Node {

    public Node0x00(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @SuppressWarnings("unused")
    @Override
    public void parseSelf() {
        byte opcode = chunk.getData(start, 1)[0];
        length = 1;
        endOfStream = start + length;
    }

    @Override
    public void parseDown() {

    }

    @Override
    public String getXml() {
        return "";
    }

}
