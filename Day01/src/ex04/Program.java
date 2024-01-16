package ex04;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        TransactionsService service = new TransactionsService();
        service.addUser(new User("Danya", 500));
        service.addUser(new User("Arslan", 300));
        service.performTransaction(1, 2, 50);
        System.out.println(service.getUsersList().getUserByID(1) + "\n" +
                service.getUsersList().getUserByID(2));
        User user = service.getUsersList().getUserByID(1);
        UUID uuid = user.getTransactionsList().toArray()[0].getIdentifier();
        service.removeTransactionOfAUser(user, uuid);
        System.out.println(service.getUnpairedTransactions()[0]);
    }
}