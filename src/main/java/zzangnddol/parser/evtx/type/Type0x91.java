package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import zzangnddol.parser.evtx.Chunk;

public class Type0x91 extends VariantType {

    public Type0x91(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        int start = this.start;
        ByteBuffer buffer = chunk.getByteBuffer(start, length);

        int elements = length / 8;
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < elements; i++) {
            if (i != 0) b.append("\n");
            Date d = new Date((buffer.getLong() / 10000L - 11644473600000L));
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            b.append(String.format("[%u] %sZ", i, DatatypeConverter.printTime(c)));
        }
        string = b.toString();
    }
}
