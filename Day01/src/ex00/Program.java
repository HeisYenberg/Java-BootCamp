package ex00;

public class Program {
    public static void main(String[] args) {
        User recipient = new User(1, "Danya", 500);
        User sender = new User(2, "Arslan", 300);
        Transaction transaction = new Transaction(recipient, sender,
                Transaction.TransferCategory.DEBIT, -50);
        System.out.println(transaction);
    }
}