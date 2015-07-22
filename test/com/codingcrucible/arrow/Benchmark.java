package com.codingcrucible.arrow;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javax.imageio.ImageIO;

public class Benchmark
{
    interface Decode
    {
        void read(InputStream is) throws IOException;
    }
    
    enum Decoder implements Decode{
        IMAGEIO("ImageIO"){
        public void read(InputStream is) throws IOException {
            ImageIO.read(is);
        }
    },
        SIXLEGS("SixLegs2"){
        private com.sixlegs.png.PngConfig config = new com.sixlegs.png.PngConfig.Builder().build();
        public void read(InputStream is) throws IOException {
            new com.sixlegs.png.PngImage(config).read(is, true);
        }
        },
            Arrow("Arrow"){
        private PngConfig config = new PngConfig.Builder().build();
        public void read(InputStream is) throws IOException {
            new PngImage(config).read(is);
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
        List<String> list = Files.readAllLines(Paths.get("benchmark.txt"));
        
        long t;
        for (Decoder d : Decoder.values()){
            t = 0;
            for (int i = 0; i < loop; i++)
                t+= benchmark(list, d);
            
            System.err.println(d.name + ": read " + (list.size() * loop) + " images in " + t + " ms");
        }
    }

    private static long benchmark(List<String> files, Decoder reader)
    throws IOException
    {
        InputStream cur;
        try {
            
            long total = 0;
            long t = 0;
            
            for (String f : files) {
                cur = new ByteArrayInputStream(Files.readAllBytes(Paths.get(f)));
                t = System.currentTimeMillis();
                reader.read(cur);
                total += System.currentTimeMillis() - t;
            }
            
            return total;
        } catch (IOException e) {
            System.err.println("Error reading");
            throw e;
        }
    }
}
