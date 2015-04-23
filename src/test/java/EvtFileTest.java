import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import zzangnddol.parser.evt.Record;

public class EvtFileTest {
    @SuppressWarnings({ "unused", "resource" })
    public static void main(String[] args) throws Exception {
        File f = new File("target/test-classes/eventfile/SecEvent.Evt");
        int i = 0;
        long offset = 0;
        long length = f.length();
        RandomAccessFile file = new RandomAccessFile(f, "r");
        byte[] h = new byte[48];
        file.read(h);
        //        System.out.println(new Header(h));
        byte[] b = new byte[8];
        int readLen = 0;
        while ((readLen = file.read(b)) >= 0) {
            ByteBuffer buffer = ByteBuffer.wrap(b).order(ByteOrder.LITTLE_ENDIAN);
            int recordLen = buffer.getInt();
            String magic = new String(b, 4, 4);
            if (magic.equals("LfLe")) {
                file.seek(file.getFilePointer() - 8);
                byte[] body = new byte[recordLen];
                file.read(body);
                Record r = new Record(0, body);
                System.out.println(r.fullString());
                //                System.out.println(r);
            }
        }
    }

    @SuppressWarnings("unused")
    private static boolean isCursorMagic(byte[] bytes) {
        //        System.out.println(HexDump.toHexString(bytes));
        for (int i = 0; i < 4; i++) {
            if (bytes[i * 4] != (byte) (0x11 * (i + 1))) return false;;
            if (bytes[i * 4 + 1] != (byte) (0x11 * (i + 1))) return false;
            if (bytes[i * 4 + 2] != (byte) (0x11 * (i + 1))) return false;
            if (bytes[i * 4 + 3] != (byte) (0x11 * (i + 1))) return false;
        }
        return true;
    }

    @SuppressWarnings("unused")
    private static long readInt(RandomAccessFile file) throws IOException {
        byte[] tmp = new byte[4];
        file.read(tmp);
        return ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    @SuppressWarnings("unused")
    private static String readMagic(RandomAccessFile file) throws IOException {
        byte[] tmp = new byte[4];
        file.read(tmp);
        return new String(tmp);
    }
}
