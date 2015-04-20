package zzangnddol.parser.evtx.node;

import zzangnddol.parser.evtx.Chunk;

public class Node0x02 extends Node {
    public Node0x02(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        byte opcode = chunk.getData(start, 1)[0];
        byte flags = (byte) (opcode >> 4);

        this.length = 1;
        chunk.setTagState(0);
    }

    @Override
    public void parseDown() {

    }

    @Override
    public String getXml() {
        return ">";
    }
}
