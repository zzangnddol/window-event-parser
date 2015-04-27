package zzangnddol.parser.evtx.node;

import zzangnddol.parser.evtx.Chunk;

public class Node0x04 extends Node {
    public Node0x04(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @SuppressWarnings("unused")
    @Override
    public void parseSelf() {
        byte opcode = chunk.getData(start, 1)[0];

        this.length = 1;
        this.tagLength = 1;
        this.dataLength = 0;
        chunk.setTagState(0);

        Node element = chunk.popElement();
        if (element instanceof Node0x01) {
            ((Node0x01)element).setElementType(1);
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
