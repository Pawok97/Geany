package LZW;

import org.junit.Test;

import java.io.*;
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

        ArrayList<Long> compressed = (ArrayList<Long>) fileReader(new File("compression file.cb"));
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

    private List<Long> fileReader(File file) throws Exception {
        String rs;
        ArrayList<Long> compressed = new ArrayList<>();

        StringBuilder line = new StringBuilder("");
        BufferedReader reader = new BufferedReader(new FileReader(file));

        while ((rs = reader.readLine()) != null) {
            line.append(rs);
        }
        String[] result=line.toString().split(",");
        for (String i:result){
            compressed.add(Long.parseLong(i));
        }
        return compressed;
    }

    private void fileWriter(String line) throws Exception {
        FileWriter writer = new FileWriter(new File("decompression file.cb"));
        writer.write(line);
        writer.flush();
    }

    @Test
    public void main() throws Exception {
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
