package ex01;

public class Egg implements Runnable {
    private final int count;
    ProducerConsumer producerConsumer;

    public Egg(int count, ProducerConsumer producerConsumer) {
        this.count = count;
        this.producerConsumer = producerConsumer;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; ++i) {
            try {
                producerConsumer.printEgg();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}