package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.EvtxUtil;

public class Template extends Node {
    private int templateId;
    private String guid;

    public Template(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        ByteBuffer data = chunk.getByteBuffer(start, 8);
        int next = data.getInt();
        int templateId = data.getInt();
        this.next = next;
        this.templateId = templateId;

        byte[] t = chunk.getData(start + 4, 16);
        this.guid = EvtxUtil.guid(t);

        this.tagLength = 24;

        data = chunk.getByteBuffer(this.start + 20, 4);
        int length = data.getInt();
        this.dataLength = length;

        this.length = this.tagLength + this.dataLength;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getTemplateGuid() {
        return this.guid;
    }

    @Override
    public String getXml() {
        String result = super.getXml();
        return result;
    }
}
