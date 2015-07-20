package com.codingcrucible.arrow;

import java.awt.image.WritableRaster;

class RasterDestination
extends Destination
{
    protected final WritableRaster raster;
    protected final int sourceWidth;
    
    public RasterDestination(WritableRaster raster, int sourceWidth)
    {
        this.raster = raster;
        this.sourceWidth = sourceWidth;
    }

    public void setPixels(int x, int y, int w, int[] pixels)
    {
        raster.setPixels(x, y, w, 1, pixels);
    }

    public void setPixel(int x, int y, int[] pixel)
    {
        raster.setPixel(x, y, pixel);
    }

    public void getPixel(int x, int y, int[] pixel)
    {
        raster.getPixel(x, y, pixel);
    }

    public WritableRaster getRaster()
    {
        return raster;
    }

    public int getSourceWidth()
    {
        return sourceWidth;
    }

    public void done()
    {
    }    
}
