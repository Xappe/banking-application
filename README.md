# Banking Application

## Description

This project is a banking application that allows value transfers between accounts. The system includes the following functionalities:

- **Account registration**  
  - Account number, agency number, client, balance, limit, account type  
- **Deposit**  
- **Withdrawal**  
- **Limit adjustment**  
- **Transfers**  
  - Value limitation according to the time of day  
- **Export transaction history (CSV)**  
- **Terminal menu for selecting the desired operation**  

---

## Features

### Account Registration
Allows registering a new bank account with information such as account number, agency number, client, balance, limit, and account type.

### Deposit
Allows making deposits into an existing bank account.

### Withdrawal
Allows making withdrawals from an existing bank account.

### Limit Adjustment
Allows adjusting the limit of a bank account.

### Transfers
Allows transferring values between bank accounts, with value limitation according to the time of day.

### Export Transaction History (CSV)
Allows exporting the transaction history of a bank account to a CSV file.

---

## 📂 Project Structure
```md
- BankingApplication/ 📁 *(Root Project Folder)*
  - src/ 📁 *(Source Code)*
    - application/ 📁 *(Main application)*
      - Program.java *(Main entry point)*
    - interfaces/ 📁 *(Interfaces for standardizing operations)*
      - AccountInterface.java
    - model/ 📁 *(Business Models)*
      - entities/ 📁 *(Core entities)*
        - Account.java
        - Client.java
        - Transaction.java
    - resources/ 📁 *(Business logic)*
      - AccountResource.java
      - ClientResource.java
      - TransactionResource.java
    - util/ 📁 *(Utilities)*
      - IDGenerator.java
  - data/ 📁 *(Database/CSV Storage)*
    - transactions.csv
  - README.md 📄 *(Project Documentation)*
  - .gitignore 📄 *(Ignored files for Git)*
  - LICENSE 📄 *(Project License)*
  - pom.xml 📄 *(Maven dependencies, if applicable)*
  - build.gradle 📄 *(Gradle dependencies, if applicable)*
```


## Class Diagram

```mermaid
classDiagram
    class Client {
        +UUID id
        +String name
        +String email
        +String cpf
    }

    class Account {
        +UUID id
        +String accountNumber
        +String accountAgency
        +Client client
        +Double balance
        +Double bound
        +String accountType
        +List<Transaction> transactions
        +Boolean transferBlocked
        +Double transferLimit
        +Double transferAccumulated
    }

    class Transaction {
        +UUID id
        +Date date
        +String transactionType
        +Double value
        +Account sourceAccount
        +Account destinationAccount
    }

    class AccountInterface {
        <<interface>>
        +createAccount(String id, String accountNumber, String accountAgency, Client client, Double balance, Double bound, String accountType, Boolean transferBlocked, Double transferLimit, Double transferAccumulated): Account
        +deposit(String accountId, Double amount)
        +withdraw(String accountId, Double amount)
        +transfer(String sourceAccountId, String destinationAccountId, Double amount)
        +blockTransfersLimit(String accountId, Double limit)
        +unblockTransfers(String accountId)
        +exportHistoryCSV(String accountId)
    }

    class AccountResource {
        -List<Account> accounts
        -TransactionResource transactionResource

        +AccountResource(TransactionResource transactionResource)
        +createAccount(String id, String accountNumber, String accountAgency, Client client, Double balance, Double bound, String accountType, Boolean transferBlocked, Double transferLimit, Double transferAccumulated): Account
        +deposit(String accountId, Double amount)
        +withdraw(String accountId, Double amount)
        +transfer(String sourceAccountId, String destinationAccountId, Double amount)
        +blockTransfersLimit(String accountId, Double limit)
        +unblockTransfers(String accountId)
        +exportHistoryCSV(String accountId)
        +getAccountById(String id): Account
        +getAllAccounts(): List<Account>
        +removeAccountById(String id)
    }

    class ClientResource {
        -List<Client> clients

        +addClient(Client client)
        +getClientById(String id): Client
        +getAllClients(): List<Client>
        +removeClientById(String id)
    }

    class TransactionResource {
        -List<Transaction> transactions

        +createTransaction(String id, String transactionType, Double value, Account sourceAccount, Account destinationAccount): Transaction
        +getTransactionById(String id): Transaction
        +getAllTransactions(): List<Transaction>
        +removeTransactionById(String id)
        +exportTransactionHistory()
    }

    class IDGenerator {
        +generateID(): String
    }

    Client "1" -- "0..*" Account: owns
    Account "1" -- "0..*" Transaction: contains
    Transaction "1" -- "1" Account: source
    Transaction "1" -- "1" Account: destination
    AccountResource ..|> AccountInterface
    AccountResource "1" -- "1" TransactionResource: uses
    AccountResource "1" -- "0..*" Account: manages
    ClientResource "1" -- "0..*" Client: manages
    TransactionResource "1" -- "0..*" Transaction: manages
```

## Sequence Diagram 

### Account Creation

```mermaid
sequenceDiagram
    actor User as "User"
    participant System as "System"
    participant Client as "Client"
    participant Account as "Account"
 
    User->>System: Requires account creation
    activate System
    
    System->>+Client: Check if the client exists
    
    alt User exists
        Client-->>-System: Client exists
    else User does not exist
        Client-->>System: Client existence not checked
        System->>+Client: Requires client creation
        Client-->>-System: Client created successfully
    end
    
    System->>+Account: Create new account
    Account-->>-System: Account created successfully
    System-->>User: Account created successfully
    deactivate System
```
### Deposit

```mermaid
sequenceDiagram
    actor User as "User"
    participant System as "System"
    participant Account as "Account"
    participant Transaction as "Transaction"

    User->>System: Make a deposit
    activate System
    System->>+Account: Check account
    alt Account checked
        Account-->>-System: Account checked
        System->>+Account: Add value to the balance
        Account-->>-System: Balance updated
        System->>+Transaction: Add transaction
        Transaction-->>-System: Transaction added
        System-->>User: Deposit successful
    else
        Account-->>System: Error: Account not found
        System-->>User: Error: Account not found
    end
    deactivate System
```

### Withdrawal

```mermaid
sequenceDiagram
    actor User as "User"
    participant System as "System"
    participant Account as "Account"
    participant Transaction as "Transaction"

    User->>System: Make a withdrawal
    activate System
    System->>+Account: Check account
    
    alt Account checked
        Account-->>-System: Account checked
        System->>+Account: Check balance
        alt Check balance
            Account-->>-System: Balance checked
            System->>+Account: Balance update
            Account-->>-System: Balance updated
            Account->>+Transaction: Add transaction
            Transaction-->>-Account: Transaction added
            Account-->>System: Withdrawal successful
            System-->>User: Withdrawal successful
        else 
            Account-->>System: Error: Insufficient balance 
            System-->>User: Error: Insufficient balance
        end
    else
        Account-->>System: Error: Account not found
        System-->>User: Error: Account not found
    end
    deactivate System
```

### Transfer

```mermaid
sequenceDiagram
    actor User as "User"
    participant System as "System"
    participant SourceAccount as "SourceAccount"
    participant DestinationAccount as "DestinationAccount"
    participant Transaction as "Transaction"

    User->>System: Request Transfer
    activate System
    System->>+SourceAccount: Check if source account exist
    alt Account exist
        SourceAccount-->>-System: Source account exist
        System->>+SourceAccount: Check balance available
            alt Sufficient balance
                SourceAccount-->>-System: Sufficient balance
                System->>+SourceAccount: Check limiter by time
                alt Authorized value
                    SourceAccount->>-System: Authorized value
                    System->>+DestinationAccount: Check if source account exist
                    alt Destination account exist
                        DestinationAccount-->>-System: Account exist
                        System->>+SourceAccount: Update balance
                        SourceAccount-->>-System: Update balance successful
                        System->>+DestinationAccount: Update balance
                        DestinationAccount-->>-System: Update balance successful
                        System->>+Transaction: Register transfer transaction
                        Transaction-->>-System: Transaction successful
                        System-->>User: Transaction successful
                    else Destination account not exist
                        DestinationAccount-->>System: Erro: Destination account not found
                        System-->>User: Erro: Destination account not found
                    end
                else Unauthorized value
                    SourceAccount-->>System: Erro: Unauthorized value
                    System-->>User: Erro: Unauthorized value
                end
            else Insufficient balance
                SourceAccount-->>System: Erro: Insufficient balance
                System-->>User: Erro: Insufficient balance
            end
    else Source account not exist
        SourceAccount-->>System: Erro: Source account not found
        System-->>User: Erro: Source account not found
    end
    deactivate System
```

### Limit Changed

```mermaid
sequenceDiagram
actor User as "User"
participant System as "System"
participant Account as "Account"

    User->>System: Request change limit
    activate System
    System->>+Account: Check Account
    
    alt Check Account
        Account-->>-System: Account checked success
        System->>+Account: Evaluate requested limit
        
        alt Evaluate requested limit
            Account-->>-System: Limit requested approved
            System-->>+Account: Update Limit
            Account-->>-System: Limit change successful
        else
            Account-->>System: Error: Limit denied
            System-->>User: Error: Limit denied
        end
    else 
        Account-->>System: Error: Account not found
        System-->>User: Error: Account not found
    end
deactivate System
```

### Transaction Historical Export (CSV)

```mermaid
sequenceDiagram
    actor User as "User"
    participant System as "System"
    participant Account as "Account"
    participant Transaction as "Transaction"

    User->>System: Requests history export csv 
    activate System
    System->>+Account: Check account
    alt Account checked
        Account-->>-System: Account checked
        
        System->>+Account: Collect transactions
        Account->>+Transaction: Request transactions
        Transaction-->>-Account: Return transactions
        Account-->>-System: Return transactions 
        System->>+System: Generate csv
        System-->>-System: Return csv transactions successful
        System-->>User: Return csv transactions successful
    else
        Account-->>System: Error: Account not found
        System-->>User: Error: Account not found
    end
    deactivate System
```
