package ex03;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String buffer;
        int week = 0;
        long grades = 0;
        while (week < 18 && !(buffer = in.nextLine()).equals("42")) {
            ++week;
            if (!buffer.equals("Week " + week)) {
                printError();
            }
            grades *= 10;
            grades += getMinimalGrade(in);
            in.nextLine();
        }
        in.close();
        printGraph(swapDigits(grades));
    }

    public static int getMinimalGrade(Scanner in) {
        int result = 0;
        for (int i = 0; i < 5; ++i) {
            if (!in.hasNextInt()) {
                printError();
            }
            int read = in.nextInt();
            if (read < 1 || read > 9) {
                printError();
            }
            if (i == 0 || result > read) {
                result = read;
            }
        }
        return result;
    }

    public static long swapDigits(long digits) {
        long result = 0, lastDigit = digits % 10;
        while (lastDigit != 0) {
            result += lastDigit;
            result *= 10;
            digits /= 10;
            lastDigit = digits % 10;
        }
        return result / 10;
    }

    public static void printGraph(long grades) {
        long lastDigit = grades % 10, week = 0;
        while (lastDigit != 0) {
            ++week;
            System.out.print("Week " + week + " ");
            for (int i = 0; i < lastDigit; ++i) {
                System.out.print("=");
            }
            System.out.println(">");
            grades /= 10;
            lastDigit = grades % 10;
        }
    }

    public static void printError() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }
}
