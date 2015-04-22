package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.NumberUtil;

public class Type0x13 extends VariantType {

    public Type0x13(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, 2);
        byte version = buffer.get();
        byte elements = buffer.get();

        buffer = chunk.getByteBuffer(start + 2, 6).order(ByteOrder.BIG_ENDIAN);
        long high = buffer.getInt() << 16 ^ buffer.getShort();
        String SID = String.format("S-%d-%s", version, high + "");

        buffer = chunk.getByteBuffer(start + 8, elements * 4);
        for (int i = 0; i < elements; i++) {
            SID += String.format("-%d", NumberUtil.unsigned32(buffer.getInt()));
        }
        string = SID;
        length = (short) (8 + elements * 4);
    }
}
