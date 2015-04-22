package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.HexDump;

public class Type0x0e extends VariantType {

    public Type0x0e(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        byte[] b = null;
        if (context == 1) {
            b = chunk.getData(start, length);
        } else {
            ByteBuffer buffer = chunk.getByteBuffer(start, 4);
            int length = buffer.getInt();
            b = chunk.getData(start + 2, length);
            this.length = (short) (2 + length);
        }
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            buffer.append(HexDump.toHexString(b[i]));
        }
        string = buffer.toString().toUpperCase();
    }
}
