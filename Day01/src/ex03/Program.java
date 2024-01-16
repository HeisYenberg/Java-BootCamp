package ex03;

import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        UsersList usersList = new UsersArrayList();
        usersList.addUser(new User("Danya", 500));
        usersList.addUser(new User("Arslan", 300));
        UUID uuid = UUID.randomUUID();
        Transaction transaction = new Transaction(uuid,
                usersList.getUserByID(1), usersList.getUserByIndex(1),
                Transaction.TransferCategory.DEBIT, -50);
        usersList.getUserByID(1).getTransactionsList()
                .addTransaction(transaction);
        Transaction transaction1 = new Transaction(uuid,
                usersList.getUserByIndex(1), usersList.getUserByID(1),
                Transaction.TransferCategory.CREDIT, 50);
        usersList.getUserByID(2).getTransactionsList()
                .addTransaction(transaction1);
        usersList.getUserByID(1).getTransactionsList()
                .removeTransaction(transaction.getIdentifier());
        System.out.println(
                usersList.getUserByID(2).getTransactionsList().toArray()[0]);
    }
}