package ex02;

public class Program {
    public static void main(String[] args) {
        String prefixSize = "--arraySize=";
        String prefixTreads = "--threadsCount=";
        if (args.length != 2 || !args[0].startsWith(prefixSize) ||
                !args[1].startsWith(prefixTreads)) {
            System.err.println("Invalid array size or threads count input");
            return;
        }
        try {
            String stringSize = args[0].substring(prefixSize.length());
            int size = Integer.parseUnsignedInt(stringSize);
            String stringThreads = args[1].substring(prefixTreads.length());
            int threads = Integer.parseUnsignedInt(stringThreads);
            if (size > 2000000 || threads > size) {
                System.err.println("Invalid array size or threads count input");
                return;
            }
            RandomArray randomArray = new RandomArray(size, threads);
            randomArray.getSums();
        } catch (NumberFormatException e) {
            System.err.println("Invalid array size or threads count input");
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
    }
}
