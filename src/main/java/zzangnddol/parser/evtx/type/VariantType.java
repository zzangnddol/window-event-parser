package zzangnddol.parser.evtx.type;

import zzangnddol.parser.evtx.Chunk;

public abstract class VariantType {
    protected byte type;
    protected Chunk chunk;
    protected int start;
    protected int length;
    protected int context;

    protected String string;

    public VariantType(byte type, Chunk chunk, int start, short length, int context) {
        this.type = type;
        this.chunk = chunk;
        this.start = start;
        this.length = length;
        this.context = context;
    }

    public abstract void parseSelf();

    public void parseDown() {

    }

    public static VariantType newVariant(byte type, Chunk chunk, int start, short length, int context) {
        VariantType variant = null;
        if (type == 0x00) {
            variant = new Type0x00(type, chunk, start, length, context);
        } else if (type == 0x01) {
            variant = new Type0x01(type, chunk, start, length, context);
        } else if (type == 0x02) {
            variant = new Type0x02(type, chunk, start, length, context);
        } else if (type == 0x03) {
            variant = new Type0x03(type, chunk, start, length, context);
        } else if (type == 0x04) {
            variant = new Type0x04(type, chunk, start, length, context);
        } else if (type == 0x05) {
            variant = new Type0x05(type, chunk, start, length, context);
        } else if (type == 0x06) {
            variant = new Type0x06(type, chunk, start, length, context);
        } else if (type == 0x07) {
            variant = new Type0x07(type, chunk, start, length, context);
        } else if (type == 0x08) {
            variant = new Type0x08(type, chunk, start, length, context);
        } else if (type == 0x09) {
            variant = new Type0x09(type, chunk, start, length, context);
        } else if (type == 0x0a) {
            variant = new Type0x0a(type, chunk, start, length, context);
        } else if (type == 0x0b) {
            variant = new Type0x0b(type, chunk, start, length, context);
        } else if (type == 0x0c) {
            variant = new Type0x0c(type, chunk, start, length, context);
        } else if (type == 0x0d) {
            variant = new Type0x0d(type, chunk, start, length, context);
        } else if (type == 0x0e) {
            variant = new Type0x0e(type, chunk, start, length, context);
        } else if (type == 0x0f) {
            variant = new Type0x0f(type, chunk, start, length, context);
        } else if (type == 0x10) {
            variant = new Type0x10(type, chunk, start, length, context);
        } else if (type == 0x11) {
            variant = new Type0x11(type, chunk, start, length, context);
        } else if (type == 0x12) {
            variant = new Type0x12(type, chunk, start, length, context);
        } else if (type == 0x13) {
            variant = new Type0x13(type, chunk, start, length, context);
        } else if (type == 0x14) {
            variant = new Type0x14(type, chunk, start, length, context);
        } else if (type == 0x15) {
            variant = new Type0x15(type, chunk, start, length, context);
        } else if (type == 0x21) {
            variant = new Type0x21(type, chunk, start, length, context);
        } else if (type == 0x81) {
            variant = new Type0x81(type, chunk, start, length, context);
        } else if (type == 0x83) {
            variant = new Type0x83(type, chunk, start, length, context);
        } else if (type == 0x84) {
            variant = new Type0x84(type, chunk, start, length, context);
        } else if (type == 0x85) {
            variant = new Type0x85(type, chunk, start, length, context);
        } else if (type == 0x86) {
            variant = new Type0x86(type, chunk, start, length, context);
        } else if (type == 0x87) {
            variant = new Type0x87(type, chunk, start, length, context);
        } else if (type == 0x88) {
            variant = new Type0x88(type, chunk, start, length, context);
        } else if (type == 0x89) {
            variant = new Type0x89(type, chunk, start, length, context);
        } else if (type == 0x8a) {
            variant = new Type0x8a(type, chunk, start, length, context);
        } else if (type == 0x8b) {
            variant = new Type0x8b(type, chunk, start, length, context);
        } else if (type == 0x8c) {
            variant = new Type0x8c(type, chunk, start, length, context);
        } else if (type == 0x8f) {
            variant = new Type0x8f(type, chunk, start, length, context);
        } else if (type == 0x91) {
            variant = new Type0x91(type, chunk, start, length, context);
        } else if (type == 0x92) {
            variant = new Type0x92(type, chunk, start, length, context);
        } else if (type == 0x94) {
            variant = new Type0x94(type, chunk, start, length, context);
        } else if (type == 0x95) {
            variant = new Type0x95(type, chunk, start, length, context);
        } else {
            //            print type, chunk->get_hexdump(start, length);
            //            my $msg = sprintf("Undefined VariantType 0x%x Start=0x%x, Length=%d",
            //                    type, start, length);
            //            confess($msg);
        }
        //        assert(defined(variant),
        //                "VariantType object not defined") if DEBUG;
        return variant;
    }

    public String getXml() {
        String str = this.string == null ? "" : this.string;
        return str;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
