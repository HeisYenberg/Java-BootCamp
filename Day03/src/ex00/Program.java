package ex00;

public class Program {
    public static void main(String[] args) {
        String prefix = "--count=";
        if (args.length != 1 || !args[0].startsWith(prefix)) {
            System.err.println("Invalid count number");
            return;
        }
        try {
            int count = Integer.parseInt(args[0].substring(prefix.length()));
            Thread egg = new Thread(new Egg(count));
            Thread hen = new Thread(new Hen(count));
            egg.start();
            hen.start();
            egg.join();
            hen.join();
            run(count);
        } catch (NumberFormatException e) {
            System.err.println("Invalid count number");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(int count) {
        for (int i = 0; i < count; ++i) {
            System.out.println("Human");
        }
    }
}