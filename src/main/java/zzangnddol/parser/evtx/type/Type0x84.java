package zzangnddol.parser.evtx.type;

import zzangnddol.parser.evtx.Chunk;

public class Type0x84 extends VariantType {

    public Type0x84(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        start = this.start;
        byte[] data = null;
        if (context == 1) {
            data = chunk.getData(start, length);
        } else {

        }
        int elements = length;
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < elements; i++) {
            if (i != 0) buffer.append("\n");
            buffer.append(String.format("[%u] %u", i, data[i]));
        }
        string = buffer.toString();
    }
}
