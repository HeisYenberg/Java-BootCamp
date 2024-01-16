package ex01;

public class Program {
    public static void main(String[] args) {
        User recipient = new User("Danya", 500);
        User sender = new User("Arslan", 300);
        Transaction transaction = new Transaction(recipient, sender,
                Transaction.TransferCategory.DEBIT, -50);
        System.out.println(transaction);
    }
}