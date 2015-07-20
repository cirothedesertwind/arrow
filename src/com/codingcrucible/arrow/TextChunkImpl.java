package com.codingcrucible.arrow;

class TextChunkImpl
implements TextChunk
{
    private final String keyword;
    private final String text;
    private final String language;
    private final String translated;
    private final int type;
    
    public TextChunkImpl(String keyword, String text, String language, String translated, int type)
    {
        this.keyword = keyword;
        this.text = text;
        this.language = language;
        this.translated = translated;
        this.type = type;
    }
    
    public String getKeyword()
    {
        return keyword;
    }

    public String getTranslatedKeyword()
    {
        return translated;
    }

    public String getLanguage()
    {
        return language;
    }

    public String getText()
    {
        return text;
    }

    public int getType()
    {
        return type;
    }
}
