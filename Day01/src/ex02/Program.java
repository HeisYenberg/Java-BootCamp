package ex02;

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
        System.out.println(transaction);
        System.out.println(usersList.getNumberOfUsers());
    }
}