package edu.school21.numbers;

import edu.school21.exeptions.IllegalNumberException;

public class NumberWorker {
    public static boolean isPrime(int number) {
        if (number < 2) {
            throw new IllegalNumberException("Invalid number input");
        }
        for (int i = 2; i * i <= number; ++i) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static int digitsSum(int number) {
        int result = 0;
        while (number != 0) {
            result += number % 10;
            number /= 10;
        }
        return result;
    }
}
