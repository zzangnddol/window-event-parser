package zzangnddol.parser.evtx.type;

import zzangnddol.parser.evtx.Chunk;

public class Type0x04 extends VariantType {

    public Type0x04(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        byte data = chunk.getData(start, 1)[0];
        string = data + "";
        length = 1;
    }
}
