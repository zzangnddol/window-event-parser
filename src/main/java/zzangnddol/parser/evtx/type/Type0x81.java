package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import zzangnddol.parser.evtx.Chunk;

public class Type0x81 extends VariantType {

    public Type0x81(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        int start = this.start;
        CharsetDecoder decoder = Charset.forName("UTF-16LE").newDecoder();
        if (context == 1) {
            ByteBuffer buffer = chunk.getByteBuffer(start, length);
            try {
                string = decoder.decode(buffer).toString();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            }
        } else {
            ByteBuffer buffer = chunk.getByteBuffer(start, 2);
            short length = buffer.getShort();
            length *= 2;
            buffer = chunk.getByteBuffer(start + 2, length);
            try {
                string = decoder.decode(buffer).toString();
            } catch (CharacterCodingException e) {
                e.printStackTrace();
            }
            this.length = (short) (length + 2);
        }
        String[] tokens = string.split("\\000");
        int i = 1;
        string = "";
        while(i <= tokens.length) {
            string += String.format("\n[%d] %s", i, tokens[i]);
            i++;
        }
    }
}
