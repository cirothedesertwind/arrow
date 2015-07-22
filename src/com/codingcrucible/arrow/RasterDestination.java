package com.codingcrucible.arrow;

import java.awt.image.WritableRaster;

class RasterDestination implements Destination
{
    protected final WritableRaster raster;
    protected final int sourceWidth;
    
    public RasterDestination(WritableRaster raster, int sourceWidth)
    {
        this.raster = raster;
        this.sourceWidth = sourceWidth;
    }

    @Override
    public void setPixels(int x, int y, int w, int[] pixels)
    {
        raster.setPixels(x, y, w, 1, pixels);
    }

    @Override
    public void setPixel(int x, int y, int[] pixel)
    {
        raster.setPixel(x, y, pixel);
    }

    @Override
    public void getPixel(int x, int y, int[] pixel)
    {
        raster.getPixel(x, y, pixel);
    }

    @Override
    public WritableRaster getRaster()
    {
        return raster;
    }

    @Override
    public int getSourceWidth()
    {
        return sourceWidth;
    }

    @Override
    public void done()
    {
    }    
}
