package ex05;

import java.util.UUID;

public class TransactionsLinkedList implements TransactionsList {
    private Node head;
    private Node tail;
    private int transactionsCount;

    @Override
    public void addTransaction(Transaction transaction) {
        Node new_node = new Node();
        new_node.transaction = transaction;
        new_node.prev = tail;
        if (tail != null) {
            tail.next = new_node;
        }
        tail = new_node;
        if (head == null) {
            head = new_node;
        }
        ++transactionsCount;
    }

    @Override
    public void removeTransaction(UUID id) {
        if (head == null) {
            throw new TransactionNotFoundException("Transactions list is " +
                    "empty");
        }
        Node temp = head;
        while (temp != null) {
            if (temp.transaction.getIdentifier().equals(id)) {
                removeNode(temp);
                break;
            }
            temp = temp.next;
        }
        if (temp == null) {
            throw new TransactionNotFoundException("Transaction id not found");
        }
    }

    @Override
    public Transaction[] toArray() {
        Transaction[] array = new Transaction[transactionsCount];
        Node temp = head;
        for (int i = 0; i < transactionsCount; ++i) {
            array[i] = temp.transaction;
            temp = temp.next;
        }
        return array;
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;
        if (prev != null) {
            prev.next = next;
        } else {
            head = next;
        }
        if (next != null) {
            next.prev = prev;
        } else {
            tail = prev;
        }
        --transactionsCount;
    }

    private static class Node {
        public Node next;
        public Node prev;
        public Transaction transaction;
    }

    public static class TransactionNotFoundException extends RuntimeException {
        TransactionNotFoundException(String message) {
            super(message);
        }
    }
}
