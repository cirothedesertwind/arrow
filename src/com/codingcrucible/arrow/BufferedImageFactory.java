/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codingcrucible.arrow;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.InputStream;

final class BufferedImageFactory {

    private static class BufferedImageBuilder {
        Raster raster;
        ColorModel model;
    }
    
     static final BufferedImage create(PngReader.Config config, InputStream is) {
        BufferedImageBuilder builder = new BufferedImageBuilder();
        //placeholder
        builder.raster = Raster.createPackedRaster(0, 0, 0, new int[0], null);
        builder.model = ColorModel.getRGBdefault();
        
        WritableRaster wRaster = builder.raster.createCompatibleWritableRaster();
        wRaster.setDataElements(0, 0, builder.raster);
        return new BufferedImage(builder.model, wRaster, builder.model.isAlphaPremultiplied(), null);
    }
    
}
