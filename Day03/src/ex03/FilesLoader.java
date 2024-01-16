package ex03;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FilesLoader {
    private final BlockingQueue<String> urls;
    private final int count;

    public FilesLoader(int count) throws FileNotFoundException {
        this.count = count;
        urls = new LinkedBlockingQueue<>();
        FileInputStream inputStream = new FileInputStream("src/ex03" +
                "/files_urls.txt");
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            urls.add(scanner.nextLine());
        }
    }

    public void start() {
        Thread[] threads = new Thread[count];
        for (int i = 0; i < count; ++i) {
            threads[i] = new Thread(new Loader(i + 1, urls));
            threads[i].start();
        }
    }
}