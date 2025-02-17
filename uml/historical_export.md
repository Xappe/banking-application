:::mermaid
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
:::