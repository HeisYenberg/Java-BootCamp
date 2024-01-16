package ex01;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        if (!in.hasNextInt()) {
            printError();
        }
        int num = in.nextInt();
        in.close();
        checkNumber(num);
    }

    public static void checkNumber(int num) {
        if (num < 2) {
            printError();
        }
        int i = 2;
        boolean status = true;
        for (; i * i <= num; ++i) {
            if (num % i == 0) {
                status = false;
                break;
            }
        }
        System.out.println(status + " " + (i - 1));
    }

    public static void printError() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }
}
