package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;

public class Node0x0c extends Node {
    private int templateId;
    private int pointer;

    public Node0x0c(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public String getXml() {
        Template template = chunk.getTemplate(this.pointer);
        return template.getXml();
    }

    @Override
    public void parseSelf() {
        ByteBuffer data = chunk.getByteBuffer(start, 10);
        byte opcode = data.get();
        byte unknown = data.get();
        int templateId = data.getInt();
        int pointer = data.getInt();

        this.tagLength = 10;
        this.dataLength = this.length - this.tagLength;
        this.templateId = templateId;
        this.pointer = pointer;
    }

    @Override
    public void parseDown() {
        if (this.pointer < this.start) {
            Template template = chunk.getTemplate(this.pointer);
            this.dataLength = 0;
            int eos = template.getEndOfStream();
            if (eos > 0) {
                this.endOfStream = this.start + this.tagLength;
            }
        } else {
            Template template = new Template(chunk, this, pointer, dataLength);
            chunk.setTemplate(pointer, template);
            template.parseSelf();
            template.parseDown();
            this.dataLength = template.getLength();
            this.endOfStream = template.getEndOfStream();
        }
        this.length = this.tagLength + this.dataLength;
    }

}
