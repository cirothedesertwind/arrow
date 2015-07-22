package com.codingcrucible.arrow;

final class ProgressivePixelProcessor implements PixelProcessor
{
    private final PixelProcessor pp;
    private final int imgWidth;
    private final int imgHeight;
    private final Destination dst;
    private final int samples;
    private final int[] pixels;
    
    public ProgressivePixelProcessor(Destination dst, PixelProcessor pp, int imgWidth, int imgHeight)
    {
        this.pp = pp;
        this.imgWidth = imgWidth;
        this.imgHeight = imgHeight;
        this.dst = dst;
        this.samples = dst.getRaster().getNumBands();
        this.pixels = new int[samples * 8];
    }
    
    @Override
    public boolean process(int[] row, int xOffset, int xStep, int yStep, int y, int width)
    {
        // run non-progressive processor first
        pp.process(row, xOffset, xStep, yStep, y, width);

        // then replicate pixels across entire block
        int blockHeight = xStep;
        int blockWidth = xStep - xOffset;
        if (blockWidth > 1 || blockHeight > 1) {
            int yMax = Math.min(y + blockHeight, imgHeight);
            for (int srcX = 0, dstX = xOffset; srcX < width; srcX++) {
                dst.getPixel(dstX, y, pixels);
                int xMax = Math.min(dstX + blockWidth, imgWidth);
                int xPixels = xMax - dstX;
                for (int i = samples, end = xPixels * samples; i < end; i++)
                    pixels[i] = pixels[i - samples];
                for (int i = y; i < yMax; i++)
                    dst.setPixels(dstX, i, xPixels, pixels);
                dstX += xStep;
            }
        }
        return true;
    }
}
