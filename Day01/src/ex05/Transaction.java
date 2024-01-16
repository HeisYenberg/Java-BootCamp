package ex05;

import java.util.UUID;

public class Transaction {
    private final UUID identifier;
    private TransferCategory transferCategory;
    private Integer transferAmount;
    private User recipient;
    private User sender;

    public Transaction(UUID identifier, User recipient, User sender,
                       TransferCategory category, Integer amount) {
        if (recipient.getIdentifier().toString()
                .equals(sender.getIdentifier().toString())) {
            printError();
        }
        if ((amount < 0 && !category.equals(TransferCategory.DEBIT)) ||
                (amount > 0 && !category.equals(TransferCategory.CREDIT))) {
            printError();
        }
        this.identifier = identifier;
        this.recipient = recipient;
        this.sender = sender;
        this.transferCategory = category;
        this.transferAmount = amount;
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public TransferCategory getTransferCategory() {
        return transferCategory;
    }

    public void setTransferCategory(TransferCategory transferCategory) {
        this.transferCategory = transferCategory;
    }

    public Integer getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(Integer transferAmount) {
        this.transferAmount = transferAmount;
    }

    private void printError() {
        System.err.println("Illegal Argument");
        System.exit(-1);
    }

    @Override
    public String toString() {
        return "To " + recipient + " " + transferAmount;
    }

    public enum TransferCategory {
        DEBIT, CREDIT
    }
}