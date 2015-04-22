package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Type0x92 extends VariantType {

    public Type0x92(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, length);

        StringBuffer b = new StringBuffer();
        int elements = length / 16;
        for (int i = 0; i < elements; i++) {
            if (i != 0) b.append("\n");
            short year = buffer.getShort();
            short month = buffer.getShort();
            short dow = buffer.getShort();
            short day = buffer.getShort();
            short h = buffer.getShort();
            short m = buffer.getShort();
            short s = buffer.getShort();
            short ms = buffer.getShort();
            b.append(String.format("[%u] %04d-%02d-%02dT%02d:%02d:%02d.%04dZ",
                    i,
                    year,
                    month,
                    day,
                    h,
                    m,
                    s,
                    ms));
        }
        string = b.toString();
    }
}
