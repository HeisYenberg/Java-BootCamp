package ex00;

public class Program {
    public static void main(String[] args) {
        int digits = 479598;
        System.out.println(sumOfDigits(digits));
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
}
