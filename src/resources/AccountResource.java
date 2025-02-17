package resources;

import interfaces.AccountOperations;
import model.entities.Account;
import model.entities.Client;
import model.entities.Transaction;
import util.IDGenerator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountResource implements AccountOperations {
    private final List<Account> accounts = new ArrayList<>();
    private final TransactionResource transactionResource;

    public AccountResource(TransactionResource transactionResource) {
        this.transactionResource = transactionResource;
    }

    @Override
    public Account createAccount(String accountNumber, String accountAgency, Client client, Double balance, Double bound, String accountType, Boolean transferBlocked, Double transferLimit, Double transferAccumulated) {
        try{
            Account newAccount = new Account(accountNumber, accountAgency, client, balance, bound, accountType, transferBlocked, transferLimit, transferAccumulated);

            newAccount.setId(IDGenerator.generateID());

            ArrayList<Transaction> transactions = new ArrayList<>();
            newAccount.setTransactions(transactions);

            addAccount(newAccount);
            return newAccount;
        }catch(Exception e){
            System.out.println(e);
          return null;
        }
    }

    public void addAccount(Account account) {
        accounts.add(account);
        System.out.println("Account add successfully: " + account);
    }

    @Override
    public void deposit(UUID accountId, Double amount) {

        Account account = getAccountById(accountId);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);

            Transaction transaction = transactionResource.createTransaction("deposit",  amount,  account,  null);

           // add transaction to transactions account list
            List<Transaction> accountTransactions = account.getTransactions();
            accountTransactions.add(transaction);
            account.setTransactions(accountTransactions);

            System.out.println("Deposit successful. New balance: " + account.getBalance());
            System.out.println(account);
        }else{
            System.out.println("Account not found!");
        }
    }

    @Override
    public void withdraw(UUID accountId, Double amount) {
        Account account = getAccountById(accountId);
        if (account != null) {
            if (account.getBalance() >= amount) {
                account.setBalance(account.getBalance() - amount);

                Transaction transaction = transactionResource.createTransaction("withdraw",  amount,  account,  null);

                // add transaction to transactions account list
                List<Transaction> accountTransactions = account.getTransactions();
                accountTransactions.add(transaction);
                account.setTransactions(accountTransactions);
                System.out.println("\nWithdrawal successful. New balance: " + account.getBalance());
            } else {
                System.out.println("\nInsufficient balance for withdrawal.");
            }
        }else{
            System.out.println("Account not found!");
        }
    }

    public void changeLimit(UUID accountId, Double newLimit) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setBound(newLimit);
            System.out.println("\nLimit changed successfully. New limit: " + account.getBound());
        }
    }

    @Override
    public void transfer(UUID sourceAccountId, UUID destinationAccountId, Double amount) {
        Account sourceAccount = getAccountById(sourceAccountId);
        Account destinationAccount = getAccountById(destinationAccountId);
        if (sourceAccount != null) {
            if (destinationAccount != null) {
                if(destinationAccount != sourceAccount){
                    // check if the balance is sufficient for transfer
                    if (sourceAccount.getBalance() >= amount) {

                        // check the date of the last transaction of the source account is today
                        if(sourceAccount.getTransactions().isEmpty() || !sourceAccount.getTransactions().getLast().getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(LocalDate.now()))  sourceAccount.setTransferAccumulated(0.0);

                        // check the time and the transfer accumulated
                        if (LocalTime.now().isAfter(LocalTime.of(9, 0)) ) {
                            if (sourceAccount.isTransferBlocked() &&  sourceAccount.getTransferAccumulated() + amount > sourceAccount.getTransferLimit()) {
                                System.out.println("\nTransfer limit exceeded for this account during blocked hours.");

                            } else {
                                sourceAccount.setBalance(sourceAccount.getBalance() - amount);
                                destinationAccount.setBalance(destinationAccount.getBalance() + amount);
                                sourceAccount.setTransferAccumulated(sourceAccount.getTransferAccumulated() + amount);

                                Transaction transaction = transactionResource.createTransaction("transfer", amount, sourceAccount, destinationAccount);

                                // add transaction to transactions account list
                                List<Transaction> sourceAccountTransactions = sourceAccount.getTransactions();
                                sourceAccountTransactions.add(transaction);
                                sourceAccount.setTransactions(sourceAccountTransactions);

                                List<Transaction> destinationAccountTransactions = destinationAccount.getTransactions();
                                destinationAccountTransactions.add(transaction);
                                destinationAccount.setTransactions(destinationAccountTransactions);

                                System.out.println("\nTransfer successful. New balance of source account: " + sourceAccount.getBalance());
                                System.out.println("\nNew balance of destination account: " + destinationAccount.getBalance());
                            }
                        }
                        else{
                            sourceAccount.setBalance(sourceAccount.getBalance() - amount);
                            destinationAccount.setBalance(destinationAccount.getBalance() + amount);
                            sourceAccount.setTransferAccumulated(0.0);

                            transactionResource.createTransaction("transfer", amount, sourceAccount, destinationAccount);

                            System.out.println("\nTransfer successful. New balance of source account: " + sourceAccount.getBalance());
                            System.out.println("\nNew balance of destination account: " + destinationAccount.getBalance());
                        }
                    } else {
                        System.out.println("\nInsufficient balance for transfer.");
                    }
                }else{
                    System.out.println("\nDestination account equal to the Source account.");
                }

            } else {
                System.out.println("\nDestination Account not found.");
            }
        } else {
            System.out.println("\nSource Account not found.");
        }
    }

    @Override
    public void blockTransfersLimit(UUID accountId, Double limit) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setTransferBlocked(true);
            account.setTransferLimit(limit);
            System.out.println("\nTransfers blocked for account ID: " + accountId + " with limit: " + limit);
        }else{
            System.out.println("Account not found!");
        }
    }

    @Override
    public void unblockTransfers(UUID accountId) {
        Account account = getAccountById(accountId);
        if (account != null) {
            account.setTransferBlocked(false);
            System.out.println("\nTransfers unblocked for account ID: " + accountId);
        } else {
            System.out.println("\nAccount not found!");
        }
    }


    @Override
    public void exportHistoryCSV(UUID accountId) {
        String csvFile = "transactions_"+accountId+".csv";
        Account account = getAccountById(accountId);
        if(account != null) {
            List<Transaction> transactions = account.getTransactions();

            if (!transactions.isEmpty()) {
                try (FileWriter writer = new FileWriter(csvFile)) {
                    writer.append("ID,Date,TransactionType,Value,SourceAccount,DestinationAccount\n");
                    for (Transaction transaction : transactions) {
                        writer.append(transaction.getId().toString()).append(",");
                        writer.append(transaction.getDate().toString()).append(",");
                        writer.append(transaction.getTransactionType()).append(",");
                        writer.append(transaction.getValue().toString()).append(",");
                        writer.append(transaction.getSourceAccount() != null ? transaction.getSourceAccount().getId().toString() : "null").append(",");
                        writer.append(transaction.getDestinationAccount() != null ? transaction.getDestinationAccount().getId().toString() : "null").append("\n");
                    }
                    System.out.println("Transaction history exported successfully to " + csvFile);
                } catch (IOException e) {
                    System.out.println("Error while exporting transaction history: " + e.getMessage());
                }
            } else {
                System.out.println("There is no transaction!");
            }
        }else{
            System.out.println("Account not found!");
        }
    }

    public Account getAccountById(UUID id) {
        System.out.print(accounts);

        for (Account account : accounts) {
            if (account.getId().equals(id)) {
                return account;
            }
        }
        System.out.println("Account not found with ID: " + id);
        return null;
    }

    public List<Account> getAllAccounts() {
        return accounts;
    }

    public void removeAccountById(UUID id) {
        accounts.removeIf(account -> account.getId().equals(id));
        System.out.println("Account removed successfully with ID: " + id);
    }

}


