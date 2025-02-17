:::mermaid
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
:::