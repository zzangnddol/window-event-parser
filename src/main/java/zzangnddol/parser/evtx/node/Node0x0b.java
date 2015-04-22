package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import zzangnddol.parser.evtx.Chunk;

public class Node0x0b extends Node {

    public Node0x0b(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        ByteBuffer data = chunk.getByteBuffer(start, 3);
        byte opcode = data.get();
        short strlen = data.getShort();

        this.tagLength = 3;
        this.dataLength = strlen * 2;
        this.flags = (byte) (opcode >> 4);
    }

    @Override
    public void parseDown() {
        if (this.dataLength > 0) {
            CharsetDecoder decoder = Charset.forName("UTF-16LE").newDecoder();
            try {
                this.string = decoder.decode(chunk.getByteBuffer(this.start + this.tagLength, this.dataLength - 4)).toString();
            } catch (CharacterCodingException e) {
            }
        } else {
            this.string = "";
        }
        this.length = this.tagLength + this.dataLength;
    }

    @Override
    public String getXml() {
        if (this.string.equals("")) {
            return "?>";
        } else {
            return String.format(" %s?>", this.string);
        }
    }
}
