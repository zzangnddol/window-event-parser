package zzangnddol.parser.evtx.node;

import zzangnddol.parser.evtx.Chunk;

public class Root extends Node {
    private SubstArray substArray;

    public Root(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        tagLength = 0;
        dataLength = length;
    }

    @Override
    public void parseDown() {
        super.parseDown();

        SubstArray array = new SubstArray(
                chunk,
                this,
                this.endOfStream,
                this.length - this.endOfStream + this.start
                );
        array.parseSelf();
        array.parseDown();
        this.substArray = array;
    }

    @Override
    public String getSubstitute(int index, int type, int optional) {
        return this.substArray.getSubstitute(index, type, optional);
    }

    @Override
    public String getXml() {
        this.chunk.pushRoot(this);
        String xml = super.getXml();
        this.chunk.popRoot();
        return xml;
    }
}
