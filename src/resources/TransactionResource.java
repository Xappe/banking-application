package resources;

import model.entities.Client;
import model.entities.Transaction;
import model.entities.Account;
import util.IDGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TransactionResource {
    private final List<Transaction> transactions = new ArrayList<>();

    public Transaction createTransaction(String transactionType, Double value, Account sourceAccount, Account destinationAccount) {
        Transaction transaction = new Transaction(new Date(), transactionType, value, sourceAccount, destinationAccount);
        transaction.setId(IDGenerator.generateID());
        addTransaction(transaction);
        return transaction;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        System.out.println("Transaction add successfully: " + transaction);
    }

    public Transaction getTransactionById(UUID id) {
        for (Transaction transaction : transactions) {
            if (transaction.getId().equals(id)) {
                return transaction;
            }
        }
        System.out.println("Transaction not found with ID: " + id);
        return null;
    }

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public void removeTransactionById(String id) {
        transactions.removeIf(transaction -> transaction.getId().equals(id));
        System.out.println("Transaction removed successfully with ID: " + id);
    }
}
