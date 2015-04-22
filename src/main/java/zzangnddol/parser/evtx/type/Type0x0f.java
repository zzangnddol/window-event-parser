package zzangnddol.parser.evtx.type;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.EvtxUtil;

public class Type0x0f extends VariantType {

    public Type0x0f(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        byte[] bytes = this.chunk.getData(start, 16);
        this.string = EvtxUtil.guid(bytes);
        length = 16;
    }
}
