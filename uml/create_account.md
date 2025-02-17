:::mermaid

sequenceDiagram
    actor User as "User"
    participant System as "System"
    participant Client as "Client"
    participant Account as "Account"
 

    User->>System: Requires account creation
    activate System
    
    System->>+Client: Check if the client exist
    
    alt User exist
        Client-->>-System: Client existence exist
    else User not exist
        Client-->>System:  Client existence not checked
        System->>+Client: Requires client creation
        Client-->>-System: Client create successful
    end
    
    System->>+Account: Create new account
    Account-->>-System: Account created successful
    System-->>User: Account created successful
    deactivate System
:::