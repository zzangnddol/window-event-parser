package zzangnddol.parser.evtx.type;

import zzangnddol.parser.evtx.Chunk;

public class Type0x00 extends VariantType {

    public Type0x00(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        if (context == 1) {

        } else {

        }
    }
}
