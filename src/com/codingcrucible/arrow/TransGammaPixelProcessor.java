package com.codingcrucible.arrow;

final class TransGammaPixelProcessor
extends BasicPixelProcessor
{
    final private short[] gammaTable;
    final private int[] trans;
    final private int shift;
    final private int max;
    final private int samplesNoAlpha;
    final private int[] temp;
    
    public TransGammaPixelProcessor(Destination dst, short[] gammaTable, int[] trans, int shift)
    {
        super(dst, dst.getRaster().getNumBands());
        this.gammaTable = gammaTable;
        this.trans = trans;
        this.shift = shift;
        max = gammaTable.length - 1;
        samplesNoAlpha = samples - 1;
        temp = new int[samples * dst.getSourceWidth()];
    }
    
    public boolean process(int[] row, int xOffset, int xStep, int yStep, int y, int width)
    {
        int total = width * samplesNoAlpha;
        for (int i1 = 0, i2 = 0; i1 < total; i1 += samplesNoAlpha, i2 += samples) {
            boolean opaque = false;
            for (int j = 0; j < samplesNoAlpha; j++) {
                int sample = row[i1 + j];
                opaque = opaque || (sample != trans[j]);
                temp[i2 + j] = 0xFFFF & gammaTable[sample >> shift];
            }
            temp[i2 + samplesNoAlpha] = opaque ? max : 0;
        }
        return super.process(temp, xOffset, xStep, yStep, y, width);
    }
}
