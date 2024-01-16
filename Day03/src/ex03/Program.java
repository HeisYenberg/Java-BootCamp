package ex03;

import java.io.FileNotFoundException;

public class Program {
    public static void main(String[] args) {
        String prefix = "--threadsCount=";
        if (args.length != 1 || !args[0].startsWith(prefix)) {
            System.err.println("Invalid input for threads count");
            return;
        }
        try {
            String stringCount = args[0].substring(prefix.length());
            int count = Integer.parseUnsignedInt(stringCount);
            FilesLoader loader = new FilesLoader(count);
            loader.start();
        } catch (NumberFormatException | FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }
}