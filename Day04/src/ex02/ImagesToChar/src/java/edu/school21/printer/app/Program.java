package edu.school21.printer.app;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import edu.school21.printer.logic.Arguments;
import edu.school21.printer.logic.ImageConverter;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        try {
            Arguments arguments = new Arguments();
            JCommander.newBuilder().addObject(arguments).build().parse(args);
            ImageConverter converter = new ImageConverter(arguments);
            converter.print();
        } catch (IOException | ParameterException e) {
            System.err.println(e.getMessage());
        }
    }
}