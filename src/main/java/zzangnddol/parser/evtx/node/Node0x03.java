package zzangnddol.parser.evtx.node;

import zzangnddol.parser.evtx.Chunk;

public class Node0x03 extends Node {

    public Node0x03(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @SuppressWarnings("unused")
    @Override
    public void parseSelf() {
        byte opcode = chunk.getData(start, 1)[0];

        this.length = 1;

        Node element = chunk.popElement();
        if (element instanceof Node0x01) {
            ((Node0x01)element).setElementType(0);
        } else {
        }
    }

    @Override
    public void parseDown() {

    }

    @Override
    public String getXml() {
        return "";
    }
}
