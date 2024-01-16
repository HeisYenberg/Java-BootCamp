package ex04;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        in.close();
        int[] letters = new int[65536];
        for (char c : input.toCharArray()) {
            ++letters[c];
        }
        int[] mostAmount = new int[10];
        char[] mostFrequent = new char[10];
        for (int i = 0; i < 10; ++i) {
            int index = findMax(letters);
            mostAmount[i] = letters[index];
            mostFrequent[i] = (char) index;
            letters[index] = 0;
        }
        printFrequency(mostAmount, mostFrequent);
    }

    public static int findMax(int[] letters) {
        int index = 0, max = letters[0];
        for (int i = 0; i < letters.length; ++i) {
            if (letters[i] > max) {
                max = letters[i];
                index = i;
            }
        }
        return index;
    }

    public static void printFrequency(int[] mostAmount, char[] mostFrequent) {
        for (int i = 11; i >= 0; --i) {
            for (int j = 0; j < 10 && mostFrequent[j] != 0; ++j) {
                if (j == 0) {
                    System.out.println();
                }
                if (mostAmount[j] * 10 / mostAmount[0] == i - 1) {
                    System.out.printf("%3d", mostAmount[j]);
                } else if (i > 0 && mostAmount[j] * 10 / mostAmount[0] >= i) {
                    System.out.print("  #");
                } else if (i == 0) {
                    System.out.print("  " + mostFrequent[j]);
                }
            }
        }
    }
}
