package com.codingcrucible.arrow;

import java.awt.image.WritableRaster;

final class SubsamplingDestination
extends RasterDestination
{
    private final int xsub;
    private final int ysub;
    private final int xoff;
    private final int yoff;
    private final int[] singlePixel;
    
    public SubsamplingDestination(WritableRaster raster, int sourceWidth,
                                  int xsub, int ysub, int xoff, int yoff)
    {
        super(raster, sourceWidth);
        this.xsub = xsub;
        this.ysub = ysub;
        this.xoff = xoff;
        this.yoff = yoff;
        singlePixel = new int[raster.getNumBands()];
    }

    public void setPixels(int x, int y, int w, int[] pixels)
    {
        if (((y - yoff) % ysub) == 0) {
            int xdst = (x - xoff) / xsub;
            int ydst = (y - yoff) / ysub;
            int startSrc = xdst * xsub + xoff;
            if (startSrc < x) {
                xdst++;
                startSrc += xsub;
            }
            int samples = raster.getNumBands();
            for (int i = startSrc - x, end = x + w; i < end; i += xsub) {
                System.arraycopy(pixels, i * samples, singlePixel, 0, samples);
                super.setPixel(xdst++, ydst, singlePixel);
            }
        }
    }

    public void setPixel(int x, int y, int[] pixel)
    {
        x -= xoff;
        y -= yoff;
        if (x % xsub == 0 && y % ysub == 0)
            super.setPixel(x / xsub, y / ysub, pixel);
    }

    public void getPixel(int x, int y, int[] pixel)
    {
        throw new UnsupportedOperationException();
    }
}
