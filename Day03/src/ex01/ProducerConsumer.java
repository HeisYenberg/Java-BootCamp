package ex01;

public class ProducerConsumer {
    private Answer answer;

    public ProducerConsumer() {
        answer = Answer.EGG;
    }

    public synchronized void printEgg() throws InterruptedException {
        if (!answer.equals(Answer.EGG)) {
            wait();
        }
        System.out.println("Egg");
        answer = Answer.HEN;
        notify();
    }

    public synchronized void printHen() throws InterruptedException {
        if (!answer.equals(Answer.HEN)) {
            wait();
        }
        System.out.println("Hen");
        answer = Answer.EGG;
        notify();
    }

    private enum Answer {
        EGG, HEN
    }
}