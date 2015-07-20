package com.codingcrucible.arrow;

/**
 * Common interface to all PNG text chunk data ({@link PngConstants#tEXt tEXt},
 * {@link PngConstants#zTXt zTXt}, {@link PngConstants#iTXt iTXt}).
 * @see PngImage#getTextChunk
 */
public interface TextChunk
{
    /** 
     * Returns the Latin-1 (ISO-8859-1) encoded keyword
     * of this TextChunk.
     */
    String getKeyword();

    /** 
     * Returns a translation of the keyword into the language
     * used by this TextChunk, or null if unspecified.
     */
    String getTranslatedKeyword();

    /** 
     * Returns the language (RFC-1766) used by the translated 
     * keyword and the text, or null if unspecified.
     */
    String getLanguage();

    /**
     * Returns the text of this TextChunk.
     */
    String getText();

    /**
     * Returns the type of this TextChunk.
     * @return 
     *    {@link PngConstants#tEXt},<br>
     *    {@link PngConstants#zTXt},<br>
     *    or {@link PngConstants#iTXt}
     */
    int getType();
}
