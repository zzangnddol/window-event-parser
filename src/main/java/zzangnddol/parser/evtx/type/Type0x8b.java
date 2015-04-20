package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Type0x8b extends VariantType {

    public Type0x8b(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        int start = this.start;
        ByteBuffer buffer = chunk.getByteBuffer(start, length);

        int elements = length / 4;
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < elements; i++) {
            if (i != 0) buf.append("\n");
            buf.append(String.format("[%u] %e", i, buffer.getFloat()));
        }
        string = buffer.toString();
    }
}
