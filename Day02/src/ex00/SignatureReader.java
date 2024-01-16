package ex00;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SignatureReader {
    private final Map<String, String> signatures;
    private OutputStream outputStream;

    public SignatureReader() throws IOException {
        InputStream inputStream = new FileInputStream("ex00/signatures.txt");
        signatures = new HashMap<>();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] lines = line.split(",");
            signatures.put(lines[0], lines[1]);
        }
        scanner.close();
        inputStream.close();
    }

    public void start() throws IOException {
        Scanner in = new Scanner(System.in);
        outputStream = new FileOutputStream("ex00/result.txt");
        String path;
        while (!(path = in.nextLine()).equals("42")) {
            try {
                processSignature(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        in.close();
        outputStream.close();
    }

    private void processSignature(String fileName) throws IOException {
        InputStream inputStream = new FileInputStream(fileName);
        StringBuilder buffer = new StringBuilder(24);
        for (int i = 0; i < 8; ++i) {
            buffer.append(String.format(" %02X", inputStream.read()));
        }
        inputStream.close();
        boolean processed = false;
        for (Map.Entry<String, String> entry : signatures.entrySet()) {
            String value = entry.getValue();
            if (value.equals(buffer.substring(0, value.length()))) {
                processed = true;
                System.out.println("PROCESSED");
                outputStream.write((entry.getKey() + '\n').getBytes());
                break;
            }
        }
        if (!processed) {
            System.out.println("UNDEFINED");
        }
    }
}