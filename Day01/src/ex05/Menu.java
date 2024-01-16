package ex05;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final TransactionsService service;
    private final boolean devMode;
    private final Scanner in;

    public Menu(boolean devMode) {
        service = new TransactionsService();
        in = new Scanner(System.in);
        this.devMode = devMode;
    }

    public void start() {
        while (true) {
            printOptions();
            try {
                int command = in.nextInt();
                if ((command == 5 && !devMode) || (command == 7 && devMode)) {
                    in.close();
                    break;
                }
                executeCommand(command);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again");
                in.next();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println(
                    "---------------------------------------------------------");
        }
    }

    private void executeCommand(int command) {
        if (command == 1) {
            addUser();
        } else if (command == 2) {
            getUserBalance();
        } else if (command == 3) {
            performTransaction();
        } else if (command == 4) {
            getUserTransactions();
        } else if (command == 5 && devMode) {
            removeTransactionOfAUser();
        } else if (command == 6 && devMode) {
            checkTransferValidity();
        } else {
            System.out.println("Invalid command, please try again");
        }
    }

    private void printOptions() {
        int option = 1;
        System.out.println(option++ + ". Add a user");
        System.out.println(option++ + ". View user balances");
        System.out.println(option++ + ". Perform a transfer");
        System.out.println(
                option++ + ". View all transactions for a specific user");
        if (devMode) {
            System.out.println(option++ + ". DEV – remove a transfer by ID");
            System.out.println(option++ + ". DEV – check transfer validity");
        }
        System.out.println(option + ". Finish execution");
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");
        String name = in.next();
        int balance = in.nextInt();
        User user = new User(name, balance);
        int userId = user.getIdentifier();
        service.addUser(user);
        System.out.println("User with id = " + userId + " is added");
    }

    private void getUserBalance() {
        System.out.println("Enter a user ID");
        int id = in.nextInt();
        User user = service.getUsersList().getUserByID(id);
        System.out.println(user.getName() + " - " + user.getBalance());
    }

    private void performTransaction() {
        System.out.println(
                "Enter a sender ID, a recipient ID, and a transfer amount");
        int sender = in.nextInt();
        int recipient = in.nextInt();
        service.performTransaction(recipient, sender, in.nextInt());
        System.out.println("The transfer is completed");
    }

    private void getUserTransactions() {
        System.out.println("Enter a user ID");
        User user = service.getUsersList().getUserByID(in.nextInt());
        Transaction[] transactions = user.getTransactionsList().toArray();
        for (Transaction transaction : transactions) {
            UUID id = transaction.getIdentifier();
            System.out.println(transaction + " with id = " + id);
        }
    }

    private void removeTransactionOfAUser() {
        System.out.println("Enter a user ID and a transfer ID");
        User user = service.getUsersList().getUserByID(in.nextInt());
        UUID id = UUID.fromString(in.next());
        Transaction[] transactions = user.getTransactionsList().toArray();
        Transaction transaction = getTransactionById(transactions, id);
        service.removeTransactionOfAUser(user, id);
        System.out.println("Transfer " + transaction + " removed");
    }

    private Transaction getTransactionById(Transaction[] transactions,
                                           UUID id) {
        Transaction result = null;
        for (Transaction transaction : transactions) {
            if (transaction.getIdentifier().equals(id)) {
                result = transaction;
                break;
            }
        }
        return result;
    }

    private void checkTransferValidity() {
        System.out.println("Check results:");
        Transaction[] transactions = service.getUnpairedTransactions();
        for (Transaction transaction : transactions) {
            User recipient = transaction.getRecipient();
            User sender = transaction.getSender();
            UUID id = transaction.getIdentifier();
            int amount = transaction.getTransferAmount();
            System.out.println(recipient + " has an unacknowledged transfer " +
                    "id = " + id + " from " + sender + " for " + amount);
        }
    }
}