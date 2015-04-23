package zzangnddol.parser.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ParseUtil {
    public static ByteBuffer asByteBuffer(byte[] bytes, ByteOrder order) {
        return ByteBuffer.wrap(bytes).order(order);
    }

    public static ByteBuffer asByteBuffer(byte[] bytes) {
        return asByteBuffer(bytes, ByteOrder.BIG_ENDIAN);
    }
}
