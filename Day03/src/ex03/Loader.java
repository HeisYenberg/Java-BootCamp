package ex03;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class Loader implements Runnable {
    private final int threadId;
    private final BlockingQueue<String> urls;

    public Loader(int threadId, BlockingQueue<String> urls) {
        this.threadId = threadId;
        this.urls = urls;
    }

    @Override
    public synchronized void run() {
        try {
            while (!urls.isEmpty()) {
                String[] idAndUrl = urls.take().split(" ");
                URL url = new URL(idAndUrl[1]);
                BufferedInputStream input =
                        new BufferedInputStream(url.openStream());
                String[] stringUrls = url.getFile().split("/");
                String file = "src/ex03/" + stringUrls[stringUrls.length - 1];
                FileOutputStream output = new FileOutputStream(file);
                String thread = "Thread-" + threadId;
                String fileNumber = " download file number " + idAndUrl[0];
                System.out.println(thread + " start" + fileNumber);
                int bufferSize = 8192;
                byte[] buffer = new byte[bufferSize];
                int b;
                while ((b = input.read(buffer, 0, bufferSize)) != -1) {
                    output.write(buffer, 0, b);
                }
                input.close();
                output.close();
                System.out.println(thread + " finish" + fileNumber);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}