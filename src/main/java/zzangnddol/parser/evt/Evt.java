package zzangnddol.parser.evt;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Evt {
    private static final String LFLE_MAGIC = "LfLe";

    private RandomAccessFile file;
    private Header header;

    private Record currentRecorder;

    public Evt(String fileName) throws IOException {
        this(new File(fileName));
    }

    public Evt(File file) throws IOException {
        init(file);
    }

    private void init(File file) throws IOException {
        this.file = new RandomAccessFile(file, "r");
        readHeader();
    }

    private void readHeader() throws IOException {
        long current = file.getFilePointer();
        file.seek(0);
        byte[] bytes = new byte[48];
        file.read(bytes);
        this.header = new Header(bytes);
        file.seek(current);
    }

    public Record nextRecord() throws IOException {
        long position = 0;
        if (currentRecorder == null) {
            // first record
            position = 48;
        } else {
            position = currentRecorder.nextRecordOffset();
        }
        file.seek(position);
        byte[] bytes = new byte[8];
        file.read(bytes);
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        int recordLength = buffer.getInt();
        String magic = new String(bytes, 4, 4);
        if (magic.equals(LFLE_MAGIC)) {
            file.seek(position);
            byte[] recordBytes = new byte[recordLength];
            file.read(recordBytes);
            currentRecorder = new Record(position, recordBytes);
            return currentRecorder;
        } else {
            return null;
        }
    }

    public Record gotoLastRecord() throws IOException {
        while(nextRecord() != null);
        return currentRecorder;
    }

    public Header getHeader() {
        return header;
    }

}
