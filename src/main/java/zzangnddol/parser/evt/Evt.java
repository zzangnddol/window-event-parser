package zzangnddol.parser.evt;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import zzangnddol.parser.util.ParseUtil;

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
        LengthAndMagic lam = readLengthAndMagic(0);
        if (lam.length == 48 && lam.magic.equals(LFLE_MAGIC)) {
            readHeader();
        }
    }

    private LengthAndMagic readLengthAndMagic(long position) throws IOException {
        file.seek(position);
        byte[] bytes = new byte[8];
        file.read(bytes);
        file.seek(position);
        return new LengthAndMagic(bytes);
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
            position = header == null ? 0 : 48;
        } else {
            position = currentRecorder.nextRecordOffset();
        }
        LengthAndMagic lam = readLengthAndMagic(position);
        if (lam.magic.equals(LFLE_MAGIC)) {
            file.seek(position);
            byte[] recordBytes = new byte[lam.length];
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

    private class LengthAndMagic {
        int length;
        String magic;

        LengthAndMagic(byte[] bytes) {
            ByteBuffer buffer = ParseUtil.asByteBuffer(bytes, ByteOrder.LITTLE_ENDIAN);
            length = buffer.getInt();
            magic = new String(bytes, 4, 4);
        }
    }
}
