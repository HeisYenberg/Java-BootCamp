package ex01;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Program requires two arguments");
            return;
        }
        try {
            SimilarityComparator comparator =
                    new SimilarityComparator(args[0], args[1]);
            comparator.compare();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
