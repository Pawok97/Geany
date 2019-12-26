package LZW;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Decompressor {

    Map<Long, String> dictionary;


    private void init() {
        dictionary = new HashMap<Long, String>();
        for (long i = 0; i < 256; i++)
            dictionary.put(i, "" + (char) i);
    }

    public void unpack() throws Exception {

        init();
        long dictSize = 256;

        ArrayList<Integer> compressed = (ArrayList<Integer>) fileReader(new File("compression file.cb"));
        String w = "" + (char) (long) compressed.remove(0);
        StringBuffer result = new StringBuffer(w);

        for (long k : compressed) {
            String entry;
            if (dictionary.containsKey(k))
                entry = dictionary.get(k);
            else if (k == dictSize)
                entry = w + w.charAt(0);
            else
                throw new IllegalArgumentException("Bad compressed k: " + k);

            result.append(entry);


            dictionary.put(dictSize++, w + entry.charAt(0));

            w = entry;
        }
        fileWriter(result.toString());
    }

    private List<Integer> fileReader(File file) throws Exception {
        Path path = Paths.get("compression file.cb");
        byte[] allBytes = Files.readAllBytes(path);
        ArrayList<Integer> integers = new ArrayList<>();
        int counter = 0;
        byte[] y = new byte[4];
        for (byte a : allBytes) {

            y[counter] = a;
            counter++;
            if (counter == 4) {
                integers.add(byteArrayToInt(y));
                counter = 0;
            }
        }
        return integers;
    }
        public int byteArrayToInt ( byte[] bytes){
            int result = 0;
            int l = bytes.length - 1;
            for (int i = 0; i < bytes.length; i++)
                if (i == l) result += bytes[i] << i * 8;
                else result += (bytes[i] & 0xFF) << i * 8;
            return result;
        }

        private void fileWriter (String line) throws Exception {
            FileWriter writer = new FileWriter(new File("decompression file.cb"));
            writer.write(line);
            writer.flush();
        }

        @Test
        public void main () throws Exception {
//        short[] buffer={82,69,68,256,258,257,259,262,261,264,260,266,263,267,265,268,271,270,273,269,266};

            Decompressor decompressor = new Decompressor();
            decompressor.unpack();
//        for (short i:buffer){
//            integers.add((int) i);
        }
//        System.out.println(decompress(integers));
//        integers=fileReader(new File("compression file.cb"));
//        System.out.println(unpack(integers));
//    }
    }
