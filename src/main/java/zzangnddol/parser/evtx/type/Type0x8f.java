package zzangnddol.parser.evtx.type;

import zzangnddol.parser.evtx.Chunk;

public class Type0x8f extends VariantType {

    public Type0x8f(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        string = "GUID";
    }
}
