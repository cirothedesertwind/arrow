package com.codingcrucible.arrow;

final class GammaPixelProcessor
extends BasicPixelProcessor
{
    final private short[] gammaTable;
    final private int shift;
    final private int samplesNoAlpha;
    final private boolean hasAlpha;
    final private boolean shiftAlpha;
    
    public GammaPixelProcessor(Destination dst, short[] gammaTable, int shift)
    {
        super(dst, dst.getRaster().getNumBands());
        this.gammaTable = gammaTable;
        this.shift = shift;
        hasAlpha = samples % 2 == 0;
        samplesNoAlpha = hasAlpha ? samples - 1 : samples; // don't change alpha channel
        shiftAlpha = hasAlpha && shift > 0;
    }
    
    @Override
    public boolean process(int[] row, int xOffset, int xStep, int yStep, int y, int width)
    {
        int total = samples * width;
        for (int i = 0; i < samplesNoAlpha; i++)
            for (int index = i; index < total; index += samples)
                row[index] = 0xFFFF & gammaTable[row[index] >> shift];
        if (shiftAlpha)
            for (int index = samplesNoAlpha; index < total; index += samples)
                row[index] >>= shift;
        return super.process(row, xOffset, xStep, yStep, y, width);
    }
}
