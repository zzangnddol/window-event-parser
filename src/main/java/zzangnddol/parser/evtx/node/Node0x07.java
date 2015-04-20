package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import zzangnddol.parser.evtx.Chunk;

public class Node0x07 extends Node {

    public Node0x07(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, 3);
        byte opcode = buffer.get();
        short strlen = buffer.getShort();

        this.tagLength = 3;
        this.dataLength = strlen * 2;
        this.flags = (byte) (opcode >> 4);
    }

    @Override
    public void parseDown() {
        CharsetDecoder decoder = Charset.forName("UTF-16").newDecoder();
        try {
            this.string = decoder.decode(chunk.getByteBuffer(this.start + this.tagLength, this.dataLength - 2)).toString();
        } catch (CharacterCodingException e) {
        }
        this.length = this.tagLength + this.dataLength;
    }

    @Override
    public String getXml() {
        String xml = String.format("<![CDATA[%s]]>", this.string);
        return xml;
    }
}
