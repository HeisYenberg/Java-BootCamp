package ex00;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            SignatureReader reader = new SignatureReader();
            reader.start();
        } catch (IOException e) {
            System.out.println("Unable to load signatures list");
        }
    }
}