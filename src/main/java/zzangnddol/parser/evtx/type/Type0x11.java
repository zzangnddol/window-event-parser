package zzangnddol.parser.evtx.type;

import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.DatatypeConverter;

import zzangnddol.parser.evtx.Chunk;

public class Type0x11 extends VariantType {

    public Type0x11(byte type, Chunk chunk, int start, short length, int context) {
        super(type, chunk, start, length, context);
    }

    @Override
    public void parseSelf() {
        ByteBuffer buffer = chunk.getByteBuffer(start, 8);
        Date date = new Date((buffer.getLong() / 10000L - 11644473600000L));
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setTimeZone(TimeZone.getTimeZone("GMT"));
        string = DatatypeConverter.printDateTime(c);
    }
}
