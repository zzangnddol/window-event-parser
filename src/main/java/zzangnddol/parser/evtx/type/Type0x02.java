package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.HexDump;

public class Type0x02 extends VariantType {

    public Type0x02(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        if (context == 1) {
            string = HexDump.toHexString(chunk.getData(start, length));
        } else {
            ByteBuffer buffer = chunk.getByteBuffer(start, 2);
            short length = buffer.getShort();
            string = new String(chunk.getData(start + 2, length));
            this.length = (short) (length + 2);
        }
    }
}
