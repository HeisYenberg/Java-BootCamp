package ex02;

public class ProducerConsumer {
    private final int threads;
    private int threadsSum;
    private int threadId;

    public ProducerConsumer(int threads) {
        threadsSum = 0;
        threadId = 1;
        this.threads = threads;
    }

    public synchronized void printThreadSum(int[] array, int start, int end,
                                            int threadId)
            throws InterruptedException {
        int sum = 0;
        for (int i = start; i < end; ++i) {
            sum += array[i];
        }
        threadsSum += sum;
        ++this.threadId;
        if (start != end) {
            System.out.printf("Thread %d from %d to %d sum is %d\n",
                    threadId, start, end - 1, sum);
        }
        notify();
    }

    public synchronized void printThreadsSum() throws InterruptedException {
        if (threadId < threads) {
            wait();
        }
        System.out.println("Sum by threads: " + threadsSum);
    }
}
