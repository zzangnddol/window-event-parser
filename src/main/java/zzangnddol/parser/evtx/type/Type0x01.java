package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import zzangnddol.parser.evtx.Chunk;

public class Type0x01 extends VariantType {

    public Type0x01(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        int start = this.start;
        CharsetDecoder decorder = Charset.forName("UTF-16LE").newDecoder();
        if (context == 1) {
            ByteBuffer buffer = chunk.getByteBuffer(start, this.length);
            try {
                string = decorder.decode(buffer).toString();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            }
        } else {
            ByteBuffer buffer = chunk.getByteBuffer(start, 2);
            short length = (short) (buffer.getShort() * 2);
            buffer = chunk.getByteBuffer(start + 2, length);
            try {
                string = decorder.decode(buffer).toString();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            }
            this.length = length + 2;
        }
    }
}
