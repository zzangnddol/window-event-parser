package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Node0x0e extends Node {
    public Node0x0e(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @SuppressWarnings("unused")
    @Override
    public void parseSelf() {
        ByteBuffer data = chunk.getByteBuffer(start, 4);
        byte opcode = data.get();
        short index = data.getShort();
        byte type = data.get();

        this.tagLength = 4;
        this.dataLength = 0;
        this.length = 4;
        this.index = index;
        this.type = type;
        this.tagState = chunk.getTagState();
    }

    @Override
    public String getXml() {
        boolean substition = true;
        String fmt = this.tagState == 0 ? "%s" : "=\"%s\"";
        String subst = null;
        if (substition) {
            subst = ((Node)this.parent).getSubstitute(this.index, this.type, 0);
        } else {
            subst = String.format("#%d (type 0x%02x, optional, args)#", this.index, this.type);
        }

        String xml = this.chunk.getDeferedOutput();
        if (subst == null || subst.equals("")) {
            return "";
        } else {
            xml += String.format(fmt, subst);
            return xml;
        }
    }
}
