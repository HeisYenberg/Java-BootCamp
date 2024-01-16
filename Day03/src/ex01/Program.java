package ex01;

public class Program {
    public static void main(String[] args) {
        String prefix = "--count=";
        if (args.length != 1 || !args[0].startsWith(prefix)) {
            System.err.println("Invalid count number");
            return;
        }
        try {
            int count = Integer.parseInt(args[0].substring(prefix.length()));
            ProducerConsumer producerConsumer = new ProducerConsumer();
            Thread egg = new Thread(new Egg(count, producerConsumer));
            Thread hen = new Thread(new Hen(count, producerConsumer));
            egg.start();
            hen.start();
        } catch (NumberFormatException e) {
            System.err.println("Invalid count number");
        }
    }
}