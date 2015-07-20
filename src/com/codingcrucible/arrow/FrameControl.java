package com.codingcrucible.arrow;

import java.awt.Rectangle;

/**
 * TODO
 */
public class FrameControl
{
    /** TODO */
    public static final int DISPOSE_NONE = 0;
    /** TODO */
    public static final int DISPOSE_BACKGROUND = 1;
    /** TODO */
    public static final int DISPOSE_PREVIOUS = 2;

    /** TODO */
    public static final int BLEND_SOURCE = 0;
    /** TODO */
    public static final int BLEND_OVER = 1;
    
    private final Rectangle bounds;
    private final float delay;
    private final int dispose;
    private final int blend;
        
    FrameControl(Rectangle bounds, float delay, int dispose, int blend)
    {
        this.bounds = bounds;
        this.delay = delay;
        this.dispose = dispose;
        this.blend = blend;
    }

    /**
     * TODO
     */
    public Rectangle getBounds()
    {
        return new Rectangle(bounds);
    }

    /**
     * TODO
     */
    public float getDelay()
    {
        return delay;
    }

    /**
     * TODO
     */
    public int getDispose()
    {
        return dispose;
    }

    /**
     * TODO
     */
    public int getBlend()
    {
        return blend;
    }

    public String toString()
    {
        return "FrameControl{bounds=" + bounds + ",delay=" + delay +
            ",dispose=" + dispose + ",blend=" + blend + "}";
    }
}
