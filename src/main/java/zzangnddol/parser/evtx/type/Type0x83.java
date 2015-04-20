package zzangnddol.parser.evtx.type;

import zzangnddol.parser.evtx.Chunk;

public class Type0x83 extends VariantType {

    public Type0x83(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        int start = this.start;
        byte[] b = null;
        if (context == 1) {
            b = chunk.getData(start, length);
        } else {

        }
        int elements = length;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < elements; i++) {
            if (i != 0) buffer.append("\n");
            buffer.append(String.format("[%u] %d", i, b[i]));
        }
        string = buffer.toString();
    }
}
