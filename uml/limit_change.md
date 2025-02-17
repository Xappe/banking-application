:::mermaid
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
:::