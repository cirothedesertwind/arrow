package com.codingcrucible.arrow;

import java.io.*;
import java.util.*;

class FrameDataInputStream
extends InputStream
{
    private final RandomAccessFile file;
    private final Iterator it;
    private InputStream in;

    public FrameDataInputStream(File file, List frameData)
    throws IOException
    {
        this.file = new RandomAccessFile(file, "r");
        this.it = frameData.iterator();
        advance();
    }

    public void close()
    throws IOException
    {
        if (in != null) {
            in.close();
            in = null;
            while (it.hasNext())
                it.next();
        }
        file.close();
    }

    private void advance()
    throws IOException
    {
        if (in != null)
            in.close();
        in = null;
        if (it.hasNext()) {
            // TODO: enable streaming
            FrameData data = (FrameData)it.next();
            file.seek(data.getOffset());
            byte[] bytes = new byte[data.getLength()];
            file.readFully(bytes);
            in = new ByteArrayInputStream(bytes);
        }
    }
        
    public int available()
    throws IOException
    {
        if (in == null)
            return 0;
        return in.available();
    }

    public boolean markSupported()
    {
        return false;
    }

    public int read()
    throws IOException
    {
        if (in == null)
            return -1;
        int result = in.read();
        if (result == -1) {
            advance();
            return read();
        }
        return result;
    }
    
    public int read(byte[] b, int off, int len)
    throws IOException
    {
        if (in == null)
            return -1;
        int result = in.read(b, off, len);
        if (result == -1) {
            advance();
            return read(b, off, len);
        }
        return result;
    }

    public long skip(long n)
    throws IOException
    {
        if (in == null)
            return 0;
        long result = in.skip(n);
        if (result != 0)
            return result;
        if (read() == -1)
            return 0;
        return 1;
    }
}
