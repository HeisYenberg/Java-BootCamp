package ex05;

public class User {
    private final Integer identifier;
    private final TransactionsList transactionsList;
    private String name;
    private Integer balance;

    public User(String name, Integer balance) {
        this.identifier = UserIdsGenerator.getInstance().generateId();
        this.name = name;
        if (balance < 0) {
            System.err.println("Illegal Argument");
            System.exit(-1);
        }
        this.balance = balance;
        transactionsList = new TransactionsLinkedList();
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public TransactionsList getTransactionsList() {
        return transactionsList;
    }

    @Override
    public String toString() {
        return name + "(id = " + identifier + ")";
    }
}