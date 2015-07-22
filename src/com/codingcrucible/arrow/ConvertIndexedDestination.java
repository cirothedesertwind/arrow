package com.codingcrucible.arrow;

import java.awt.image.*;

class ConvertIndexedDestination
extends Destination
{
    private final Destination dst;
    private final IndexColorModel srcColorModel;
    private final int srcSamples;
    private final int dstSamples;
    private final int sampleDiff;
    private final int[] row;
    
    public ConvertIndexedDestination(Destination dst, int width, IndexColorModel srcColorModel, ComponentColorModel dstColorModel)
    {
        this.dst = dst;
        this.srcColorModel = srcColorModel;
        srcSamples = srcColorModel.getNumComponents();
        dstSamples = dstColorModel.getNumComponents();
        sampleDiff = srcSamples - dstSamples;
        row = new int[width * dstSamples + sampleDiff];
    }

    @Override
    public void setPixels(int x, int y, int w, int[] pixels)
    {
        for (int i = w - 1, off = dstSamples * i; i >= 0; i--, off -= dstSamples)
            srcColorModel.getComponents(pixels[i], row, off);
        if (sampleDiff != 0)
            System.arraycopy(row, sampleDiff, row, 0, dstSamples * w);
        dst.setPixels(x, y, w, row);
    }

    @Override
    public void setPixel(int x, int y, int[] pixel)
    {
        setPixels(x, y, 1, pixel);
    }

    @Override
    public void getPixel(int x, int y, int[] pixel)
    {
        // TODO: convert backwards (requires looking up palette index)
        throw new UnsupportedOperationException("implement me");
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
