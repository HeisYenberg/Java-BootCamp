package ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int count = 0, query = 0;
        while (query != 42) {
            if (!in.hasNextInt()) {
                printError();
            }
            query = in.nextInt();
            if (isPrime(sumOfDigits(query))) {
                ++count;
            }
        }
        in.close();
        System.out.println("Count of coffee-request – " + count);
    }

    public static int sumOfDigits(int digits) {
        int result = 0, lastDigit = digits % 10;
        while (lastDigit != 0) {
            result += lastDigit;
            digits /= 10;
            lastDigit = digits % 10;
        }
        return result;
    }

    public static boolean isPrime(int number) {
        if (number < 2) {
            printError();
        }
        for (int i = 2; i * i <= number; ++i) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void printError() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }
}
