package com.codingcrucible.arrow;

import java.awt.image.WritableRaster;
import java.awt.Rectangle;

final class SourceRegionDestination
extends Destination
{
    private final Destination dst;
    private final int xoff, yoff, xlen, ylen, samples;
    
    public SourceRegionDestination(Destination dst, Rectangle sourceRegion)
    {
        this.dst = dst;
        xoff = sourceRegion.x;
        yoff = sourceRegion.y;
        xlen = sourceRegion.width;
        ylen = sourceRegion.height;
        samples = dst.getRaster().getNumBands();
    }

    @Override
    public void setPixels(int x, int y, int w, int[] pixels)
    {
        if (y >= yoff && y < yoff + ylen) {
            int newx = Math.max(x, xoff);
            int neww = Math.min(x + w, xoff + xlen) - newx;
            if (neww > 0) {
                if (newx > x)
                    System.arraycopy(pixels, newx * samples, pixels, 0, neww * samples);
                dst.setPixels(newx - xoff, y - yoff, neww, pixels);
            }
        }
    }

    @Override
    public void setPixel(int x, int y, int[] pixel)
    {
        x -= xoff;
        y -= yoff;
        if (x >= 0 && y >= 0 && x < xlen && y < ylen)
            dst.setPixel(x, y, pixel);
    }

    @Override
    public void getPixel(int x, int y, int[] pixel)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public WritableRaster getRaster()
    {
        return dst.getRaster();
    }

    @Override
    public int getSourceWidth()
    {
        return dst.getSourceWidth();
    }

    @Override
    public void done()
    {
        dst.done();
    }    
}
