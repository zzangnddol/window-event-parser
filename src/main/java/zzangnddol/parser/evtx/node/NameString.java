package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import zzangnddol.parser.evtx.Chunk;

public class NameString extends Node {

    public NameString(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @SuppressWarnings("unused")
    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, 8);
        int next = buffer.getInt();
        short hash = buffer.getShort();
        short length = buffer.getShort();

        this.tagLength = 8;
        this.dataLength = (length + 1) * 2;

        try {
            CharsetDecoder decoder = Charset.forName("UTF-16LE").newDecoder();
            this.string = decoder.decode(chunk.getByteBuffer(start + 8, length * 2)).toString();
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }

        this.next = next;
        this.length = this.tagLength + this.dataLength;
    }

    @Override
    public void parseDown() {

    }

    @Override
    public String getXml() {
        return this.string;
    }
}
