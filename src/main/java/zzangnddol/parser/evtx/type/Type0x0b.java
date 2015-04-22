package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Type0x0b extends VariantType {

    public Type0x0b(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, 4);
        string = String.format("%e", buffer.getFloat());
        length = 4;
    }
}
