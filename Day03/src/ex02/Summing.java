package ex02;

public class Summing implements Runnable {
    private final ProducerConsumer producerConsumer;
    private final int[] array;
    private final int start;
    private final int end;
    private final int threadId;

    public Summing(ProducerConsumer producerConsumer, int[] array, int start,
                   int end, int threadId) {
        this.producerConsumer = producerConsumer;
        this.array = array;
        this.start = start;
        this.end = end;
        this.threadId = threadId;
    }

    @Override
    public synchronized void run() {
        try {
            producerConsumer.printThreadSum(array, start, end, threadId);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
