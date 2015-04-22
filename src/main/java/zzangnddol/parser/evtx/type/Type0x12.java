package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Type0x12 extends VariantType {

    public Type0x12(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, 16);
        string = String.format("%04d-%02d-%02dT%02d:%02d:%02d.%04dZ",
                buffer.getShort(),
                buffer.getShort(),
                buffer.getShort(),
                buffer.getShort(),
                buffer.getShort(),
                buffer.getShort(),
                buffer.getShort(),
                buffer.getShort()
                );
        length = 16;
    }
}
