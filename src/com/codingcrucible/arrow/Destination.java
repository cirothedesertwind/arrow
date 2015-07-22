package com.codingcrucible.arrow;

import java.awt.image.WritableRaster;

interface Destination
{
    abstract public void setPixels(int x, int y, int w, int[] pixels); // TODO: change to setRow(int y, int w, int[] pixels)
    abstract public void setPixel(int x, int y, int[] pixel);
    abstract public void getPixel(int x, int y, int[] pixel); // used only by ProgressivePixelProcessor
    abstract public WritableRaster getRaster();
    abstract public int getSourceWidth();
    abstract public void done();
}
