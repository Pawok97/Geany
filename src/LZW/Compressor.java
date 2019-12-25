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

        int[] out=new int[k+1];
        for (i = 0; i <= k; i++) {
            out[i] = '\uffff' & tmp[i];
            line.append(out[i] + ",");
        }
        this.fileWriter(line.toString());

    }


    private void fileWriter(String array) throws Exception {
        FileOutputStream fis=new FileOutputStream(new File("compression file.cb"));
        fis.write(array.getBytes());
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
        for(int i = 0; i < 4; i++) {
            result[i] = (byte)(value & 0xFF);
            value >>>= 8;
        }
        return result;
    }
    public  int byteArrayToInt(byte[] bytes) {
        int result = 0;
        int l = bytes.length - 1;
        for(int i = 0; i < bytes.length; i++)
            if(i == l) result += bytes[i] << i * 8;
            else result += (bytes[i] & 0xFF) << i * 8;
        return result;
    }

    @Test
    public void main() throws Exception {
//        byte[] vectors=new byte [1];
//        byte[] decompress=new byte[1];
//        vectors[0]= (byte) 240;
//
//        fileWriter(vectors);
//      byte[] newByte=fileReader(new File("geany.cb")).getBytes();
//
//      for (byte b: newByte){
//          System.out.println(b);
//      }
//      fileWriter(newByte);
    }
}
