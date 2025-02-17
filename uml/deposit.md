:::mermaid
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
:::