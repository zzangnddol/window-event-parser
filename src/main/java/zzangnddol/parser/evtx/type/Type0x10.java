package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Type0x10 extends VariantType {

    public Type0x10(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        int size = length;
        long data = 0;
        if (size == 4) {
            ByteBuffer buffer = chunk.getByteBuffer(start, 4);
            data = buffer.getInt();
        } else if (size == 8) {
            ByteBuffer buffer = chunk.getByteBuffer(start, 8);
            data = buffer.getLong();
        }
        string = data + "";
    }
}
