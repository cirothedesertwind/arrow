/*
com.sixlegs.png - Java package to read and display PNG images
Copyright (C) 1998-2006 Chris Nokleberg

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version.
*/

package com.codingcrucible.arrow;

import java.awt.image.*;

class ConvertIndexedDestination
extends Destination
{
    private final Destination dst;
    private final IndexColorModel srcColorModel;
    private final int srcSamples;
    private final int dstSamples;
    private final int sampleDiff;
    private final int[] row;
    
    public ConvertIndexedDestination(Destination dst, int width, IndexColorModel srcColorModel, ComponentColorModel dstColorModel)
    {
        this.dst = dst;
        this.srcColorModel = srcColorModel;
        srcSamples = srcColorModel.getNumComponents();
        dstSamples = dstColorModel.getNumComponents();
        sampleDiff = srcSamples - dstSamples;
        row = new int[width * dstSamples + sampleDiff];
    }

    public void setPixels(int x, int y, int w, int[] pixels)
    {
        for (int i = w - 1, off = dstSamples * i; i >= 0; i--, off -= dstSamples)
            srcColorModel.getComponents(pixels[i], row, off);
        if (sampleDiff != 0)
            System.arraycopy(row, sampleDiff, row, 0, dstSamples * w);
        dst.setPixels(x, y, w, row);
    }

    public void setPixel(int x, int y, int[] pixel)
    {
        setPixels(x, y, 1, pixel);
    }

    public void getPixel(int x, int y, int[] pixel)
    {
        // TODO: convert backwards (requires looking up palette index)
        throw new UnsupportedOperationException("implement me");
    }

    public WritableRaster getRaster()
    {
        return dst.getRaster();
    }

    public int getSourceWidth()
    {
        return dst.getSourceWidth();
    }

    public void done()
    {
        dst.done();
    }    
}