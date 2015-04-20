package zzangnddol.parser.evtx.node;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import zzangnddol.parser.evtx.Chunk;
import zzangnddol.parser.evtx.type.VariantType;

public class SubstArray extends Node {
    private int elementCount;
    private List<Short> elementSize = new ArrayList<Short>();
    private List<Byte> elementType = new ArrayList<Byte>();

    private Map<Integer, VariantType> children = new HashMap<Integer, VariantType>();

    public SubstArray(Chunk chunk, Object parent, int start, int length) {
        super(chunk, parent, start, length);
    }

    @Override
    public void parseSelf() {
        int dataPos = start;
        int dataLen = length;

        ByteBuffer buffer = chunk.getByteBuffer(dataPos, 4);
        elementCount = buffer.getInt();
        dataPos += 4;
        dataLen -= 4;
        for (int i = 0; i < elementCount; i++) {
            buffer = chunk.getByteBuffer(dataPos, 4);
            elementSize.add(buffer.getShort());
            elementType.add(buffer.get());
            byte unknown = buffer.get();
            dataPos += 4;
        }
        dataLen -= 4* elementCount;
        dataLength = dataLen;
        tagLength = dataPos - this.start;
    }

    @Override
    public void parseDown() {
        int dataPos = start + tagLength;
        int dataLen = dataLength;
        for (int i = 0; i < elementCount; i++) {
            VariantType child = VariantType.newVariant(
                    this.elementType.get(i),
                    this.chunk,
                    dataPos,
                    this.elementSize.get(i),
                    1);
            child.parseSelf();
            dataPos += child.getLength();
            dataLen -= child.getLength();

            this.children.put(i, child);
        }
    }

    @Override
    public String getSubstitute(int index, int type, int optional) {
        String xml = "";
        Object obj = this.children.get(index);
        if (obj instanceof Node) {
            xml = ((Node)obj).getXml();
        } else if (obj instanceof VariantType) {
            xml = ((VariantType)obj).getXml();
        }
        return xml;
    }

    @Override
    public String getXml() {
        return "";
    }
}
