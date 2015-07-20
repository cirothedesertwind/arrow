package com.codingcrucible.arrow;

class SuggestedPaletteImpl
implements SuggestedPalette
{
    private final String name;
    private final int sampleDepth;
    private final byte[] bytes;
    private final int entrySize;
    private final int sampleCount;
        
    public SuggestedPaletteImpl(String name, int sampleDepth, byte[] bytes)
    {
        this.name = name;
        this.sampleDepth = sampleDepth;
        this.bytes = bytes;
        entrySize = (sampleDepth == 8) ? 6 : 10;
        sampleCount = bytes.length / entrySize;
    }

    public String getName()
    {
        return name;
    }
        
    public int getSampleCount()
    {
        return sampleCount;
    }
        
    public int getSampleDepth()
    {
        return sampleDepth;
    }

    public void getSample(int index, short[] pixel)
    {
        int from = index * entrySize;
        if (sampleDepth == 8) {
            for (int j = 0; j < 4; j++) {
                int a = 0xFF & bytes[from++];
                pixel[j] = (short)a;
            }
        } else {
            for (int j = 0; j < 4; j++) {
                int a = 0xFF & bytes[from++];
                int b = 0xFF & bytes[from++];
                pixel[j] = (short)((a << 8) | b);
            }
        }
    }
        
    public int getFrequency(int index)
    {
        int from = (index + 1) * entrySize - 2;
        int a = 0xFF & bytes[from];
        int b = 0xFF & bytes[from + 1];
        return ((a << 8) | b);
    }
}
