package ex01;

public class Hen implements Runnable {
    private final int count;
    ProducerConsumer producerConsumer;

    public Hen(int count, ProducerConsumer producerConsumer) {
        this.count = count;
        this.producerConsumer = producerConsumer;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; ++i) {
            try {
                producerConsumer.printHen();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}