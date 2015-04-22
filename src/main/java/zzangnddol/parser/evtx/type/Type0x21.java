package zzangnddol.parser.evtx.type;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.node.Root;

public class Type0x21 extends VariantType {
    private Root pointer;

    public Type0x21(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        Root root = new Root(chunk, this, start, length);
        root.parseSelf();
        root.parseDown();
        this.pointer = root;
    }

    @Override
    public String getXml() {
        return this.pointer.getXml();
    }
}
