package com.codingcrucible.arrow;

/**
 * A suggested palette. Suggested palettes can be useful
 * when the display device is not capable of displaying
 * the full range of colors present in the image. 
 * @see PngImage#getProperty
 * @see PngConstants#SUGGESTED_PALETTES
 */
public interface SuggestedPalette
{
    /**
     * Returns palette name. This is any convenient name for
     * referring to the palette. The name will be unique across all
     * suggested palettes in the same image.
     * @return 
     */
    String getName();

    /**
     * Returns the number of samples.
     * @return 
     */
    int getSampleCount();

    /**
     * Returns the sample depth. This specifies the width of each color and alpha component
     * of each sample in this palette.
     * @return 8 or 16
     */
    int getSampleDepth();

    /**
     * Retrieve a sample value. The red, green, blue, and alpha components of the sample
     * at the given index are stored into the short array. Each component is of the depth
     * specified by {@link #getSampleDepth getSampleDepth}. The color samples are not
     * premultiplied by alpha. An alpha value of 0 means fully transparent.
     * @throws IndexOutOfBoundsException if index < 0, index >= {@link #getSampleCount getSampleCount}, or
     * {@code pixel.length} is less than 4
     * @throws NullPointerException if {@code pixel} is null
     * @param index the sample index
     * @param pixel the array in which to store the sample components
     */
    void getSample(int index, short[] pixel);

    /**
     * Retrieve a sample frequency value. The frequency value is proportional to the
     * fraction of pixels in the image that are closest to that palette entry in RGBA
     * space. The range of individual values will reasonably fill 0 to 65535.
     * Entries appear in decreasing order of frequency.
     * @param index the sample index
     * @return 
     */
    int getFrequency(int index);
}
