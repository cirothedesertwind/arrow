/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.codingcrucible.arrow;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class PngReader {
    
    private static final PngReader.Config DEFAULT = new PngReader.Config();

    private static final long LIMIT = 1048576L; //One MiB
   
    private PngReader() {
    }
    
    public static final BufferedImage read(File f) throws IOException{
        return read(DEFAULT, f);
    }
    
    public static final BufferedImage read(InputStream is){
        return read(DEFAULT, is);
    }
    
    public static final BufferedImage read(PngReader.Config config, File f) throws FileNotFoundException, IOException{
        if (!f.exists() || !f.isFile())
            throw new FileNotFoundException(f.toString());
        
         if (f.length() < LIMIT)
            return read(config, new ByteArrayInputStream(Files.readAllBytes(f.toPath())));
        else
            return read(config, new FileInputStream(f));
    }
    
    public static final BufferedImage read(PngReader.Config config, InputStream is){
        return BufferedImageFactory.create(config, is);
    }

    public static class Config {

        public Config() {
            
        }
    }
}
