package ex01;

import java.io.*;
import java.util.*;

public class SimilarityComparator {
    private final BufferedWriter bufferedWriter;
    private final List<String> file1Words;
    private final List<String> file2Words;
    private final Set<String> dictionary;

    public SimilarityComparator(String file1, String file2) throws IOException {
        BufferedReader bufferedReader1 =
                new BufferedReader(new FileReader(file1));
        BufferedReader bufferedReader2 =
                new BufferedReader(new FileReader(file2));
        bufferedWriter = new BufferedWriter(new FileWriter(
                "ex01/dictionary.txt"));
        dictionary = new HashSet<>();
        file1Words = new LinkedList<>();
        file2Words = new LinkedList<>();
        addToDictionary(bufferedReader1, file1Words);
        addToDictionary(bufferedReader2, file2Words);
    }

    public void compare() throws IOException {
        int dictionarySize = dictionary.size();
        String[] dictionaryArray =
                dictionary.toArray(new String[dictionarySize]);
        double numerator = 0;
        double denominator1 = 0;
        double denominator2 = 0;
        for (int i = 0; i < dictionarySize; ++i) {
            bufferedWriter.write(dictionaryArray[i] + '\n');
            int count1 = getOccurrences(file1Words, dictionaryArray[i]);
            int count2 = getOccurrences(file2Words, dictionaryArray[i]);
            numerator += count1 * count2;
            denominator1 += count1 * count1;
            denominator2 += count2 * count2;
        }
        bufferedWriter.close();
        double denominator = Math.sqrt(denominator1) * Math.sqrt(denominator2);
        double similarity = numerator / denominator;
        System.out.printf("Similarity = %.2f\n", similarity);
    }

    private void addToDictionary(BufferedReader reader, List<String> fileWords)
            throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] words = line.split(" ");
            Collections.addAll(fileWords, words);
            Collections.addAll(dictionary, words);
        }
        reader.close();
    }

    private int getOccurrences(List<String> file, String word) {
        int count = 0;
        for (String string : file) {
            if (string.equals(word)) {
                ++count;
            }
        }
        return count;
    }
}