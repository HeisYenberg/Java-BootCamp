package ex05;

import java.util.UUID;

public class TransactionsService {
    private final UsersList usersList;

    public TransactionsService() {
        usersList = new UsersArrayList();
    }

    public UsersList getUsersList() {
        return usersList;
    }

    public void addUser(User user) {
        if (user == null) {
            throw new IllegalTransactionException("User doesnt exist");
        }
        usersList.addUser(user);
    }

    public Integer getUserBalance(User user) {
        if (user == null) {
            throw new IllegalTransactionException("User doesnt exist");
        }
        return user.getBalance();
    }

    public void performTransaction(Integer recipientId, Integer senderId,
                                   Integer amount) {
        if (recipientId.equals(senderId) || amount.equals(0)) {
            throw new IllegalTransactionException("Invalid transaction " +
                    "arguments");
        }
        Transaction.TransferCategory recipientCategory =
                Transaction.TransferCategory.DEBIT;
        Transaction.TransferCategory senderCategory =
                Transaction.TransferCategory.CREDIT;
        if (amount > 0) {
            recipientCategory = Transaction.TransferCategory.CREDIT;
            senderCategory = Transaction.TransferCategory.DEBIT;
        }
        User recipient = usersList.getUserByID(recipientId);
        User sender = usersList.getUserByID(senderId);
        if (recipient.getBalance() + amount < 0 ||
                sender.getBalance() - amount < 0) {
            throw new IllegalTransactionException("Transaction amount exceeds" +
                    " users balance");
        }
        UUID uuid = UUID.randomUUID();
        recipient.getTransactionsList().addTransaction(new Transaction(uuid,
                recipient, sender, recipientCategory, amount));
        sender.getTransactionsList().addTransaction(new Transaction(uuid,
                recipient, sender, senderCategory, -amount));
        recipient.setBalance(recipient.getBalance() + amount);
        sender.setBalance(sender.getBalance() - amount);
    }

    public Transaction[] getUnpairedTransactions() {
        TransactionsList list = new TransactionsLinkedList();
        Transaction[] transactions = getAllTransactions().toArray();
        for (int i = 0; i < transactions.length; ++i) {
            boolean status = false;
            for (int j = 0; j < transactions.length; ++j) {
                if (i == j) {
                    continue;
                }
                if (transactions[i].getIdentifier()
                        .equals(transactions[j].getIdentifier())) {
                    status = true;
                    break;
                }
            }
            if (!status) {
                list.addTransaction(transactions[i]);
            }
        }
        return list.toArray();
    }

    public void removeTransactionOfAUser(User user, UUID id) {
        user.getTransactionsList().removeTransaction(id);
    }

    private TransactionsList getAllTransactions() {
        TransactionsList list = new TransactionsLinkedList();
        for (int i = 0; i < usersList.getNumberOfUsers(); ++i) {
            User user = usersList.getUserByIndex(i);
            Transaction[] transactions = user.getTransactionsList().toArray();
            for (Transaction transaction : transactions) {
                list.addTransaction(transaction);
            }
        }
        return list;
    }

    public static class IllegalTransactionException extends RuntimeException {
        public IllegalTransactionException(String message) {
            super(message);
        }
    }
}
