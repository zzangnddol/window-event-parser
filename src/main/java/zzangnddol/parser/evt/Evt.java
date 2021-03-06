package zzangnddol.parser.evt;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zzangnddol.parser.util.ParseUtil;

public class Evt {
    private Logger logger = LoggerFactory.getLogger(Evt.class);

    private static final String LFLE_MAGIC = "LfLe";

    private File file;
    private RandomAccessFile randomAccessFile;
    private Header header;

    private Record currentRecorder;

    public Evt(String fileName) throws IOException {
        this(new File(fileName));
    }

    public Evt(File file) throws IOException {
        this.file = file;
        init(file);
    }

    private void init(File file) throws IOException {
        this.randomAccessFile = new RandomAccessFile(file, "r");
        LengthAndMagic lam = readLengthAndMagic(0);
        if (lam.length == 48 && lam.magic.equals(LFLE_MAGIC)) {
            readHeader();
        }
    }

    private LengthAndMagic readLengthAndMagic(long position) throws IOException {
        randomAccessFile.seek(position);
        byte[] bytes = new byte[8];
        randomAccessFile.read(bytes);
        randomAccessFile.seek(position);
        return new LengthAndMagic(bytes);
    }

    private void readHeader() throws IOException {
        long current = randomAccessFile.getFilePointer();
        randomAccessFile.seek(0);
        byte[] bytes = new byte[48];
        randomAccessFile.read(bytes);
        this.header = new Header(bytes);
        randomAccessFile.seek(current);
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
            randomAccessFile.seek(position);
            byte[] recordBytes = new byte[lam.length];
            randomAccessFile.read(recordBytes);
            currentRecorder = new Record(position, recordBytes);
            return currentRecorder;
        } else {
            return null;
        }
    }

    public Record gotoLastRecord() throws IOException {
        long current = System.currentTimeMillis();
        long beforePosition = 0;
        LengthAndMagic lam = null;
        try {
            long position = 0;
            while(true) {
                lam = readLengthAndMagic(position);
                if (!lam.magic.equals(LFLE_MAGIC)) break;
                beforePosition = position;
                position += lam.length;
            }
            lam = readLengthAndMagic(beforePosition);
            currentRecorder = new Record(beforePosition, readRecordBytes(beforePosition, lam.length));
            return currentRecorder;
        } catch(OutOfMemoryError e) {
            logger.warn("OutOfMemoryError occured: {}, position: {}, lam: {}", file.getAbsolutePath(), beforePosition, lam);
            logger.warn(e.getMessage(), e);
            throw e;
        } finally {
            logger.debug("Window EVT gotoLastRecord elapsed time: {} ms", System.currentTimeMillis() - current);
        }
    }

    private byte[] readRecordBytes(long position, int length) throws IOException {
        byte[] bytes = new byte[length];
        randomAccessFile.seek(position);
        randomAccessFile.read(bytes);
        return bytes;
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

        @Override
        public String toString() {
            return "Length: " + length + ", Magic: " + magic;
        }
    }

    public Record getCurrentRecorder() {
        return currentRecorder;
    }
}
