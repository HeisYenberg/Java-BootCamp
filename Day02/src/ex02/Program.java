package ex02;

import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        String prefix = "--current-folder=";
        if (args.length != 1 || !args[0].startsWith(prefix)) {
            System.out.println("FileManager: invalid starting directory");
            return;
        }
        String directory = args[0].substring(prefix.length());
        try {
            FileManager fileManager = new FileManager(directory);
            fileManager.start();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}