package com.codingcrucible.arrow;

import java.io.*;
import java.util.zip.*;

/**
 * An input stream used to read PNG chunk data.
 * @see PngConstants#read
 * @see #getRemaining
 */
final class PngInputStream
extends InputStream
implements DataInput
{
    private final CRC32 crc = new CRC32();
    private final InputStream in;
    private final DataInputStream data;
    private final byte[] tmp = new byte[0x1000];
    private long total;
    private int length;
    private int left;

    public PngInputStream(InputStream in)
    throws IOException
    {
        this.in = in;
        data = new DataInputStream(this);
        left = 8;
        long sig = readLong();
        if (sig != PngConstants.SIGNATURE) {
            throw new PngException("Improper signature, expected 0x" +
                                   Long.toHexString(PngConstants.SIGNATURE) + ", got 0x" +
                                   Long.toHexString(sig), true);
        }
        total += 8;
    }

    public int startChunk()
    throws IOException
    {
        left = 8; // length, type
        length = readInt();
        if (length < 0)
            throw new PngException("Bad chunk length: " + (0xFFFFFFFFL & length), true);
        crc.reset();
        int type = readInt();
        left = length;
        total += 8;
        return type;
    }
    
    public int endChunk(int type)
    throws IOException
    {
        if (getRemaining() != 0)
            throw new PngException(PngConstants.getChunkName(type) + " read " + (length - left) + " bytes, expected " + length, true);
        left = 4;
        int actual = (int)crc.getValue();
        int expect = readInt();
        if (actual != expect)
            throw new PngException("Bad CRC value for " + PngConstants.getChunkName(type) + " chunk", true);
        total += length + 4;
        return actual;
    }

    ////////// count/crc InputStream methods //////////

    public int read()
    throws IOException
    {
        if (left == 0)
            return -1;
        int result = in.read();
        if (result != -1) {
            crc.update(result);
            left--;
        }
        return result;
    }
    
    public int read(byte[] b, int off, int len)
    throws IOException
    {
        if (len == 0)
            return 0;
        if (left == 0)
            return -1;
        int result = in.read(b, off, Math.min(left, len));
        if (result != -1) {
            crc.update(b, off, result);
            left -= result;
        }
        return result;
    }

    public long skip(long n)
    throws IOException
    {
        int result = read(tmp, 0, (int)Math.min(tmp.length, n));
        return (result < 0) ? 0 : result;
    }

    public void close()
    {
        throw new UnsupportedOperationException("do not close me");
    }
    
    ////////// DataInput methods we implement directly //////////

    public boolean readBoolean()
    throws IOException
    {
        return readUnsignedByte() != 0;
    }

    public int readUnsignedByte()
    throws IOException
    {
        int a = read();
        if (a < 0)
            throw new EOFException();
        return a;
    }

    public byte readByte()
    throws IOException
    {
        return (byte)readUnsignedByte();
    }

    public int readUnsignedShort()
    throws IOException
    {
        int a = read();
        int b = read();
        if ((a | b) < 0)
            throw new EOFException();
        return (a << 8) + (b << 0);
    }

    public short readShort()
    throws IOException
    {
        return (short)readUnsignedShort();
    }

    public char readChar()
    throws IOException
    {
        return (char)readUnsignedShort();
    }

    public int readInt()
    throws IOException
    {
        int a = read();
        int b = read();
        int c = read();
        int d = read();
        if ((a | b | c | d) < 0)
            throw new EOFException();
        return ((a << 24) + (b << 16) + (c << 8) + (d << 0));
    }

    public long readLong()
    throws IOException
    {
        return ((0xFFFFFFFFL & readInt()) << 32) | (0xFFFFFFFFL & readInt());
    }

    public float readFloat()
    throws IOException
    {
        return Float.intBitsToFloat(readInt());
    }

    public double readDouble()
    throws IOException
    {
        return Double.longBitsToDouble(readLong());
    }
    
    ////////// DataInput methods we delegate //////////

    public void readFully(byte[] b)
    throws IOException
    {
        data.readFully(b, 0, b.length);
    }
    
    public void readFully(byte[] b, int off, int len)
    throws IOException
    {
        data.readFully(b, off, len);
    }

    public int skipBytes(int n)
    throws IOException
    {
        return data.skipBytes(n);
    }

    public String readLine()
    throws IOException
    {
        return data.readLine();
    }

    public String readUTF()
    throws IOException
    {
        return data.readUTF();
    }

    ////////// PNG-specific methods //////////

    /**
     * Returns the number of bytes of chunk data that the
     * {@link PngConstants#read} method implementation is required to read.
     * Use {@link #skipBytes} to skip the data.
     * @return the number of bytes in the chunk remaining to be read
     */
    public int getRemaining()
    {
        return left;
    }

    public long getOffset()
    {
        return total;
    }    
}
