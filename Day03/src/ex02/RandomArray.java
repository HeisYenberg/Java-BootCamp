package ex02;

import java.util.Arrays;
import java.util.Random;

public class RandomArray {
    private final int[] array;
    private final int threads;

    public RandomArray(int size, int threads) {
        final int maxElement = 1000;
        Random random = new Random();
        array = new int[size];
        for (int i = 0; i < size; ++i) {
            array[i] = random.nextInt(maxElement * 2) - maxElement;
        }
        this.threads = threads;
    }

    public void getSums() throws InterruptedException {
        int sum = Arrays.stream(array).sum();
        System.out.println("Sum: " + sum);
        Thread[] summingThreads = new Thread[threads];
        ProducerConsumer consumer = new ProducerConsumer(threads);
        int start = 0;
        int step = (array.length + threads - 1) / threads;
        for (int i = 0; i < threads; ++i) {
            int end = Math.min(start + step, array.length);
            Runnable runnable = new Summing(consumer, array, start, end, i + 1);
            summingThreads[i] = new Thread(runnable);
            summingThreads[i].start();
            summingThreads[i].join();
            start = end;
        }
        consumer.printThreadsSum();
    }
}