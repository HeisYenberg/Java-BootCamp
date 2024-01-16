package edu.school21.numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NumberWorkerTest {
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 5, 7, 11, 13, 17, 19, 23})
    public void isPrimeTrue(int number) {
        Assertions.assertTrue(NumberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 6, 8, 9, 10, 12, 14, 15, 16})
    public void isPrimeFalse(int number) {
        Assertions.assertFalse(NumberWorker.isPrime(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-100, -50, -21, 0, 1})
    public void isPrimeThrow(int number) {
        Assertions.assertThrows(IllegalNumberException.class,
                () -> NumberWorker.isPrime(number));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")
    public void digitsSumTest(int number, int result) {
        Assertions.assertEquals(NumberWorker.digitsSum(number), result);
    }
}
