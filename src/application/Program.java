package application;

import model.entities.Client;
import model.entities.Account;
import resources.ClientResource;
import resources.AccountResource;
import resources.TransactionResource;

import java.util.Scanner;
import java.util.UUID;

public class Program {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClientResource clientResource = new ClientResource();
        TransactionResource transactionResource = new TransactionResource();
        AccountResource accountResource = new AccountResource(transactionResource);

        // Mock, if want test software
        //initializeDefaultAccounts(clientResource, accountResource);

        while (true) {
            System.out.println("=== Bank Menu ===");
            System.out.println("1. Signup Account");
            System.out.println("2. Deposit");
            System.out.println("3. withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Limit config");
            System.out.println("6. Export transactions history");
            System.out.println("7. Exit");
            System.out.print("Select a option: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    signupAccount(scanner, clientResource, accountResource);
                    break;
                case 2:
                    deposit(scanner, accountResource);
                    break;
                case 3:
                    withdraw(scanner, accountResource);
                    break;
                case 4:
                    transfer(scanner, accountResource, transactionResource);
                    break;
                case 5:
                    int optionConfig = 0;
                    while (optionConfig != 3) {
                        System.out.println("=== Limit config ===");
                        System.out.println("1. Activate/Change transfer limit");
                        System.out.println("2. Deactivate limit");
                        System.out.println("3. Back to menu");
                        optionConfig = scanner.nextInt();

                        switch (optionConfig) {
                            case 1:
                                lockTransfersLimit(scanner, accountResource);
                                break;
                            case 2:
                                unlockTransfersLimit(scanner, accountResource);
                                break;
                            case 3:
                                break;
                            default:
                                System.out.println("Invalid option. Try again.");
                        }
                    }
                    break;
                case 6:
                    exportTransactionsHistory(scanner, accountResource);
                    break;
                case 7:
                    System.out.println("Exit...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void signupAccount(Scanner scanner, ClientResource clientResource, AccountResource accountResource) {
        System.out.print("Insert client name: ");
        String name = scanner.next();
        System.out.print("Insert client CPF: ");
        String cpf = scanner.next();
        System.out.print("Insert client email: ");
        String email = scanner.next();
        Client client = clientResource.createClient(name, cpf, email);

        System.out.print("\nInsert account number: ");
        String accountNumber = scanner.next();
        System.out.print("Insert agency account: ");
        String accountAgency = scanner.next();
        System.out.print("Insert type account: ");
        String accountType = scanner.next();

        Account account = accountResource.createAccount(accountNumber, accountAgency, client, 0.0, 0.0, accountType, false, 0.0,0.0);
        System.out.println(account);
    }

    private static void deposit(Scanner scanner, AccountResource accountResource) {
        System.out.print("Insert account ID: ");
        UUID accountId = UUID.fromString(scanner.next());
        System.out.print("Insert deposit value: ");
        Double amount = scanner.nextDouble();

        accountResource.deposit(accountId, amount);
    }

    private static void withdraw(Scanner scanner, AccountResource accountResource) {
        System.out.print("Insert account ID: ");
        UUID accountId = UUID.fromString(scanner.next());
        System.out.print("Insert withdraw value: ");
        Double amount = scanner.nextDouble();
        accountResource.withdraw(accountId, amount);
    }

    private static void transfer(Scanner scanner, AccountResource accountResource, TransactionResource transactionResource) {
        System.out.print("Insert source account ID: ");
        UUID sourceAccountId = UUID.fromString(scanner.next());
        System.out.print("Insert destiny account ID: ");
        UUID destinationAccountId = UUID.fromString(scanner.next());
        System.out.print("Insert transfer value: ");
        Double amount = scanner.nextDouble();

        accountResource.transfer(sourceAccountId, destinationAccountId, amount);
    }

    private static void unlockTransfersLimit(Scanner scanner, AccountResource accountResource) {
        System.out.print("Insert account ID: ");
        UUID accountId = UUID.fromString(scanner.next());

        accountResource.unblockTransfers(accountId);
    }

    private static void lockTransfersLimit(Scanner scanner, AccountResource accountResource) {
        System.out.print("Insert account ID: ");
        UUID accountId = UUID.fromString(scanner.next());
        System.out.print("Insert transfer limit after 18:00: ");
        Double limit = scanner.nextDouble();


        accountResource.blockTransfersLimit(accountId, limit);
    }

    private static void exportTransactionsHistory(Scanner scanner,AccountResource accountResource) {
        System.out.print("Insert account ID: ");
        UUID accountId = UUID.fromString(scanner.next());
        accountResource.exportHistoryCSV(accountId);
    }

    private static void initializeDefaultAccounts(ClientResource clientResource, AccountResource accountResource) {

        Client client1 = clientResource.createClient("1", "1", "1");
        Client client2 = clientResource.createClient("2", "2", "2");


        Account account1 = accountResource.createAccount("1", "001", client1, 1000.0, 500.0, "Corrente", false, 0.0,0.0);
        Account account2 = accountResource.createAccount("2", "002", client2, 2000.0, 1000.0, "Poupança", false, 0.0, 0.0);

        System.out.println("\n");
        System.out.println(account1);
        System.out.println(account2);
    }
}
