package ex02;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FileManager {
    private Path directory;

    public FileManager(String path) throws IOException {
        directory = Paths.get(path);
        if (!Files.isDirectory(directory)) {
            throw new IOException("FileManager: not a directory: " + path);
        }
    }

    public void start() {
        System.out.println(directory.toAbsolutePath().normalize());
        Scanner in = new Scanner(System.in);
        String command;
        while (!(command = in.nextLine()).equals("exit")) {
            String[] args = command.split(" ");
            if (args.length == 1 && args[0].equals("ls")) {
                lsCommand();
            } else if (args.length == 2 && args[0].equals("cd")) {
                cdCommand(args[1]);
            } else if (args.length == 3 && args[0].equals("mv")) {
                mvCommand(args[1], args[2]);
            } else {
                System.out.println(
                        "FileManager: command not found :" + command);
            }
        }
    }

    private void lsCommand() {
        File[] files = new File(directory.toString()).listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.printf("%s %.1g KB\n", file.getName(),
                        file.length() / (double) 1024);
            }
        }
    }

    private void cdCommand(String path) {
        Path newDirectory = directory.resolve(path);
        if (!new File(newDirectory.toString()).isDirectory()) {
            System.err.println("FileManager: not a directory: " + path);
        } else {
            directory = newDirectory;
        }
        System.out.println(directory.toAbsolutePath().normalize());
    }

    private void mvCommand(String from, String to) {
        Path pathFrom = directory.resolve(from);
        Path pathTo = directory.resolve(to);
        try {
            if (Files.isDirectory(pathTo)) {
                Files.move(pathFrom, pathTo.resolve(pathFrom.getFileName()),
                        StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.move(pathFrom, pathTo.resolveSibling(to));
            }
        } catch (IOException e) {
            System.out.println("FileManager: failed to access '" + to +
                    "': Not a directory");
        }
    }
}