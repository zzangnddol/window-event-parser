package zzangnddol.parser.evtx.node;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.type.VariantType;

public class Node0x05 extends Node {
    private VariantType pointer;

    public Node0x05(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        byte[] data = chunk.getData(start, 2);
        byte opcode = data[0];
        byte type = data[1];

        this.tagLength = 2;
        this.dataLength = this.length -2;
        this.type = type;
        this.tagState = chunk.getTagState();
    }

    @Override
    public void parseDown() {
        VariantType pointer = VariantType.newVariant(this.type, chunk, this.start + 2, (short) dataLength, 0);
        pointer.parseSelf();
        pointer.parseDown();

        this.dataLength = pointer.getLength();
        this.pointer = pointer;
        this.length = this.tagLength + this.dataLength;
    }

    @Override
    public String getXml() {
        String fmt = this.tagState == 0 ? "%s" : "=\"%s\"";
        String xml = this.chunk.getDeferedOutput();
        boolean values = true;
        if (values) {
            xml += String.format(fmt, this.pointer.getXml());
        } else {
            xml += String.format(fmt, "...");
        }
        return xml;
    }
}
