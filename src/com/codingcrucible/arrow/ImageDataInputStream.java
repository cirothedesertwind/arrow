package com.codingcrucible.arrow;

import java.io.*;

class ImageDataInputStream
extends InputStream
{
    private final PngInputStream in;
    private final StateMachine machine;
    private final byte[] onebyte = new byte[1];
    private boolean done;

    public ImageDataInputStream(PngInputStream in, StateMachine machine)
    {
        this.in = in;
        this.machine = machine;
    }
    
    public int read()
    throws IOException
    {
        return (read(onebyte, 0, 1) == -1) ? -1 : 0xFF & onebyte[0];
    }

    public int read(byte[] b, int off, int len)
    throws IOException
    {
        if (done)
            return -1;
        try {
            int total = 0;
            while ((total != len) && !done) {
                while ((total != len) && in.getRemaining() > 0) {
                    int amt = Math.min(len - total, in.getRemaining());
                    in.readFully(b, off + total, amt);
                    total += amt;
                }
                if (in.getRemaining() <= 0) {
                    in.endChunk(machine.getType());
                    machine.nextState(in.startChunk());
                    done = machine.getType() != PngConstants.IDAT;
                }
            }
            return total;
        } catch (EOFException e) {
            done = true;
            return -1;
        }
    }
}
