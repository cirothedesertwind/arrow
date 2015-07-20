package com.codingcrucible.arrow;

import java.io.*;

class FrameData
{
    private final long offset;
    private final int length;

    public FrameData(long offset, int length)
    {
        this.offset = offset;
        this.length = length;
    }

    public long getOffset()
    {
        return offset;
    }

    public int getLength()
    {
        return length;
    }
}
