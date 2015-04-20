package zzangnddol.parser.evtx;

public class EvtxUtil {
    private static final String GUID_FORMAT = "{%8s-%4s-%4s-%4s-%12s}";
    private static final int[] splitLength = {4, 2, 2, 2, 6};

    /**
     * 16byte를 GUID형식으로 만든다.
     * @param bytes
     * @return
     */
    public static String guid(byte[] bytes) {
        HexDump.nibbleChange(bytes, 0, 8);
        String[] tmp = new String[splitLength.length];
        int offset = 0;
        for (int i = 0; i < splitLength.length; i++) {
            tmp[i] = HexDump.toHexString(bytes, offset, splitLength[i]);
            offset += splitLength[i];
        }
        for (int i = 0; i < 3; i++) {
            tmp[i] = new StringBuilder(tmp[i]).reverse().toString();
        }
        return String.format(GUID_FORMAT, tmp);
    }
}
