package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            ImageConverter converter = new ImageConverter(args);
            converter.print();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}