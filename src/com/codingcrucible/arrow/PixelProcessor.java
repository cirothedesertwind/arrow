package com.codingcrucible.arrow;

// not an interface for performance
abstract class PixelProcessor
{
    abstract public boolean process(int[] row, int xOffset, int xStep, int yStep, int y, int width);
}
