package com.codingcrucible.arrow;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * The superclass for all decoding exceptions.
 */
public class PngException
extends IOException
{
    // We use reflection for pre-1.4 JVMs
    private static final Method initCause = getInitCause();

    private static Method getInitCause() {
        try {
            return PngException.class.getMethod("initCause", new Class[] {Throwable.class});
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    private final boolean fatal;
    
    PngException(String message, boolean fatal)
    {
        this(message, null, fatal);
    }

    PngException(String message, Throwable cause, boolean fatal)
    {
        super(message);
        this.fatal = fatal;
        if (cause != null && initCause != null) {
            try {
                initCause.invoke(this, new Object[] {cause});
            } catch (RuntimeException e) {
                throw e;
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IllegalStateException("Error invoking initCause: " + e.getMessage());
            }
        }
    }

    /**
     * Returns true if this exception represents a fatal decoding error.
     * Most errors in non-ancillary chunks are fatal.
     */
    public boolean isFatal()
    {
        return fatal;
    }
}
