package LZW;

import com.sun.source.tree.BinaryTree;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class Compressor {
    Vector vector;


    private void init() {
        vector = new Vector();

        for (int b = 0; b < 256; b++) {
            String tmp = new String(new byte[]{(byte) b});
            vector.addElement(tmp);
        }
    }


    public void pack() throws Exception {
        init();

        StringBuilder line = new StringBuilder("");
        byte[] iText = fileReader(new File("geany.cb")).getBytes();
        int len = iText.length;
        int[] tmp = new int[len];

        int i = 0;
        int k = 0;
        byte c = iText[i];
        String w = new String(new byte[]{c});
        do {

            i++;
            c = iText[i];

            tmp[k] = vector.indexOf(w);

            w = w + new String(new byte[]{c});
            if ((!vector.contains(w))) {

                vector.addElement(w);
                k++;
                w = new String(new byte[]{c});
            }

        } while (i < len - 1);

        tmp[k] = vector.indexOf(w);
        byte[] result = new byte[0];
        byte[] bytes = new byte[0];
        int[] out = new int[k + 1];
        for (i = 0; i <= k; i++) {
            bytes = intToByteArray(tmp[i]);
            result = ArrayUtils.addAll(result, bytes);
        }
        fileWriter(result);

    }


    private void fileWriter(String line) throws Exception {
        FileOutputStream fis = new FileOutputStream(new File("compression file.cb"));
        fis.write(line.getBytes());
        fis.flush();
    }

    private void fileWriter(byte[] line) throws Exception {
        FileOutputStream fis = new FileOutputStream(new File("compression file.cb"));
        fis.write(line);
        fis.flush();
    }

    private String fileReader(File file) throws Exception {
        String rs;
        StringBuilder line = new StringBuilder("");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        while ((rs = reader.readLine()) != null) {
            line.append(rs);
        }
        return line.toString();
    }

    public  byte[] intToByteArray(int value) {
        byte[] result = new byte[4];
        for (int i = 0; i < 4; i++) {
            result[i] = (byte) (value & 0xFF);
            value >>>= 8;
        }
        return result;
    }


    @Test
    public void main() throws Exception {
        int[] num = {150, 200, 5000, 4567, 2356};
        byte[] bytes;

        byte[] result = new byte[0];
        for (int i : num) {
            bytes =intToByteArray(i);
            result = ArrayUtils.addAll(result, bytes);
        }
//        fileWriter(result);
//        byte[] bs = fileReader(new File("compression file.cb")).getBytes();
//        int ints=
//        byte[] y=new byte [4];
//        ArrayList<Integer> integers = new ArrayList<>();
//        int counter=0;
//        for (byte a:result){
//
//            y[counter]=a;
//            counter++;
//            if (counter==4){
//                integers.add(byteArrayToInt(y));
//                counter=0;
//            }
//
//        }
    }
}
