package filehandler;

import LZW.Decompressor;
import entropy.Entropy;
import entropy.Symbol;
import LZW.Compressor;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class FileHandler {
    private ArrayList<Double> probabilitiesToEntropy = new ArrayList<>();
    private List<Integer> symbols = new ArrayList<>();

    private String randoms(ArrayList<String> probabilities, String[] quantitySymbols) {

        StringBuilder line = new StringBuilder();

        for (int i = 0; i < probabilities.size(); i++) {

            probabilities.set(i, String.valueOf((Double.parseDouble(probabilities.get(i)) / 100)));
            symbols.add(i + 65);
            probabilitiesToEntropy.add(Double.parseDouble(probabilities.get(i)));
        }

        do {
            for (int i = 0; i < symbols.size(); i++) {

                if ((Math.random() < Double.parseDouble(probabilities.get(i)) ? 1 : 0) == 1) {
                    int num;
                    num = symbols.get(i);
                    line.append((char) num);

                }
            }

        } while (line.length() != Integer.parseInt(quantitySymbols[0]));

        return line.toString();
    }


    private void writeFile(String line, String[] list) throws IOException {
        FileWriter writer = new FileWriter(new File(list[0]));
        writer.write(line);
        writer.flush();
    }


    private List<String> readFile() throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter file name:");
        String name = scanner.nextLine();


        BufferedReader reader = new BufferedReader(new FileReader(name));
        StringBuilder result = new StringBuilder();
        String rs;
        ArrayList<String> list = new ArrayList<>();
        while ((rs = reader.readLine()) != null) {
            if (rs.charAt(rs.length() - 1) == '_') {
                list.add(result.toString());
                result = new StringBuilder();
            } else {
                result.append(rs);

            }

        }
        ArrayList<String> probabilities = new ArrayList<>();
        String[] p = list.get(0).split(";");
        Collections.addAll(probabilities, p);
        String[] quantitySymbols = list.get(1).split(";");
        String[] fileName = list.get(2).split(";");
        writeFile(randoms(probabilities, quantitySymbols), fileName);
        return probabilities;
    }

    private void calculateEntropy() throws Exception {
        Entropy entropy = new Entropy();

        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        ArrayList<Double> newProbabities = new ArrayList<>();
        int quantity = 0;

        System.out.println("probabilities entropy -> " + decimalFormat.format(entropy.calcEntropyProbabilities(probabilitiesToEntropy)));

        for (Symbol s : entropy.symbolsCalculate(symbols)) {

            quantity += s.getCounter();

        }
        for (Symbol s : entropy.symbolsCalculate(symbols)) {

            newProbabities.add((double) s.getCounter() / quantity);

        }
        System.out.println("text entropy -> " + decimalFormat.format(entropy.calcEntropyProbabilities(newProbabities)));
        System.out.println("quantity symbols -> " + quantity);
        for (Symbol s : entropy.symbolsCalculate(symbols)) {

            System.out.println("symbol '" + s.getSymbol() + "' quantity -> " + s.getCounter());
        }

    }

    private void weightFile(File file) {
        System.out.println("weight '" + file.getName() + "' -> " + file.length() + " byte");
    }


    public void run() throws Exception {
        readFile();
        calculateEntropy();

        Compressor compressor = new Compressor();
        compressor.pack();

        Decompressor decompressor = new Decompressor();
        decompressor.unpack();

        weightFile(new File("geany.cb"));
        weightFile(new File("compression file.cb"));
        weightFile(new File("decompression file.cb"));

    }
}
// todo кол-во символов, вероятности появления символов, частоту появления символов


