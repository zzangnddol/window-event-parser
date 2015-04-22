package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Node0x0d extends Node {

    public Node0x0d(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        ByteBuffer data = chunk.getByteBuffer(start, 4);
        byte opcode = data.get();
        short index = data.getShort();
        byte type = data.get();

        this.tagLength = 4;
        this.dataLength= 0;
        this.length = 4;
        this.index = index;
        this.type = type;
        this.tagState = chunk.getTagState();
    }

    @Override
    public String getXml() {
        String fmt = null;
        boolean substitution = true;
        if (substitution) {
            fmt = this.tagState == 0 ? "%s" :"=\"%s\"";
        } else {
            fmt = this.tagState == 0 ? "#%d (type 0x%02x)#" : "=\"#%d (type 0x%02x)#\"";
        }
        String xml = this.chunk.getDeferedOutput();
        if (substitution) {
            xml += String.format(fmt, ((Node)this.parent).getSubstitute(this.index, this.type, 0));
        } else {
            xml += String.format(fmt, this.index, this.type);
        }
        return xml.trim();
    }
}
