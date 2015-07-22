package com.codingcrucible.arrow;

class BasicPixelProcessor implements PixelProcessor
{
    protected final Destination dst;
    protected final int samples;
    
    public BasicPixelProcessor(Destination dst, int samples)
    {
        this.dst = dst;
        this.samples = samples;
    }
    
    @Override
    public boolean process(int[] row, int xOffset, int xStep, int yStep, int y, int width)
    {
        if (xStep == 1) {
            dst.setPixels(xOffset, y, width, row);
        } else {
            int dstX = xOffset;
            for (int index = 0, total = samples * width; index < total; index += samples) {
                for (int i = 0; i < samples; i++)
                    row[i] = row[index + i];
                dst.setPixel(dstX, y, row);
                dstX += xStep;
            }
        }
        return true;
    }
}
