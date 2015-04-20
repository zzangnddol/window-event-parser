package zzangnddol.parser.evtx;

public class NumberUtil {
    public static long unsigned32(int n) {
        return n & 0xFFFFFFFFL;
    }
}
