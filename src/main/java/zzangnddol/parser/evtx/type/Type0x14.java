package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.HexDump;

public class Type0x14 extends VariantType {

    public Type0x14(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, 4);
        string = String.format("0x%s%s", HexDump.toHexString(buffer.getShort()), HexDump.toHexString(buffer.getShort()));
        length = 4;
    }
}
