package com.codingcrucible.arrow;

interface PixelProcessor
{
    boolean process(int[] row, int xOffset, int xStep, int yStep, int y, int width);
}
