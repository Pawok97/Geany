package entropy;

import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Entropy {


    private static double log(Double x) {

        return (Math.log(x) / Math.log(2) + 1e-10);
    }

    public double calcEntropyProbabilities(ArrayList<Double> probabilities) {
        double entropy = 0;
        for (Double probability : probabilities) {
//            if (Double.isNaN(probabilities.get(i))) {
//                probabilities.set(i, (double) 0);
//            }
            if (probability > 0) {
                entropy -= probability * log(probability);
            }
        }
        return entropy;
    }

    public List<Symbol> symbolsCalculate(List<Integer> symbols) throws IOException {
        FileReader reader = new FileReader(new File("geany.cb"));
        int c;
        int num = 0;
        List<Symbol> newSymbols = new ArrayList<>();

        for (Integer integer : symbols) {
            Symbol symbol = new Symbol();
            num = integer;
            symbol.setSymbol((char) num);
            symbol.setCounter(0);
            newSymbols.add(symbol);
        }

        while ((c = reader.read()) != -1) {
            for (Symbol newSymbol : newSymbols) {


                if (c == newSymbol.getSymbol()) {
                    newSymbol.setCounter(newSymbol.getCounter() + 1);
                }


            }

        }


        return newSymbols;
    }

    @Test
    public void test() throws IOException {
        List<Integer> simbols = new ArrayList<>();
        simbols.add(65);
        simbols.add(66);
        simbols.add(67);
        symbolsCalculate(simbols);
    }

}
