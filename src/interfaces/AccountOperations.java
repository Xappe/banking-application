package interfaces;

import model.entities.Account;
import model.entities.Client;

import java.util.UUID;

public interface AccountOperations {
    Account createAccount(String accountNumber, String accountAgency, Client client, Double balance, Double bound, String accountType, Boolean transferBlocked, Double transferLimit, Double transferAccumulated);
    void deposit(UUID accountId, Double amount);
    void withdraw(UUID accountId, Double amount);
    void transfer(UUID sourceAccountId, UUID destinationAccountId, Double amount);
    void blockTransfersLimit(UUID accountId, Double limit);
    void unblockTransfers(UUID accountId);
    void exportHistoryCSV(UUID accountId);
}