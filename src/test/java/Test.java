import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Test {
    public static void main(String[] args) throws Exception {
        File f = new File("target/test-classes/eventfile/SecEvent.Evt");
        RandomAccessFile file = new RandomAccessFile(f, "r");
        while(file.getFilePointer() <= file.length()) {
            long current = file.getFilePointer();
            System.out.println(String.format("current positon: %d, 0x%x", current, current));
            long len = readInt(file);
            String magic = readMagic(file);
            if (!magic.equals("LfLe")) break;
            System.out.println("len: " + len + ", magic: " + magic);
            file.seek(file.getFilePointer() + (len - 8));
        }
    }

    private static long readInt(RandomAccessFile file) throws IOException {
        byte[] tmp = new byte[4];
        file.read(tmp);
        return ByteBuffer.wrap(tmp).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    private static String readMagic(RandomAccessFile file) throws IOException {
        byte[] tmp = new byte[4];
        file.read(tmp);
        return new String(tmp);
    }
}
