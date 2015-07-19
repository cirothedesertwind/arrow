package com.sixlegs.png;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

public class Benchmark
{
    interface Decode
    {
        void read(File file) throws IOException;
    }
    
    enum Decoder implements Decode{
    TOOLKIT("Toolkit"){
        private MediaTracker tracker = new MediaTracker(new Component(){});
        private Toolkit toolkit = Toolkit.getDefaultToolkit();
        public void read(File file) throws IOException {
            try {
                tracker.addImage(toolkit.createImage(file.toURL()), 0);
                tracker.waitForID(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    },
        IMAGEIO("ImageIO"){
        public void read(File file) throws IOException {
            ImageIO.read(file);
        }
    },
            SIXLEGS("SixLegs2"){
        private PngConfig config = new PngConfig.Builder().build();
        public void read(File file) throws IOException {
            new PngImage(config).read(file);
        }
        };
    
      private final String name;

        private Decoder(String name) {
            this.name = name;
        }
      
    }
    
    public static void main(String[] args)
    throws Exception
    {
        int loop = (args.length > 0) ? Integer.parseInt(args[0]) : 1;
        BufferedReader r = new BufferedReader(new FileReader("benchmark.txt"));
        List list = new ArrayList();
        String line;
        while ((line = r.readLine()) != null)
            list.add(new File(line));
        File[] files = (File[])list.toArray(new File[list.size()]);
        
        long t;
        for (Decoder d : Decoder.values()){
            t = 0;
            for (int i = 0; i < loop; i++)
                t+= benchmark(files, d);
            
            System.err.println(d.name + ": read " + (files.length * loop) + " images in " + t + " ms");
        }
    }

    private static long benchmark(File[] files, Decoder reader)
    throws IOException
    {
        File cur = null;
        try {
            
            long t = System.currentTimeMillis();
            
                for (int j = 0; j < files.length; j++) {
                    cur = files[j];
                    reader.read(cur);
                }
            
            return System.currentTimeMillis() - t;
        } catch (IOException e) {
            System.err.println("Error reading " + cur);
            throw e;
        }
    }
}
