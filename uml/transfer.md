:::mermaid
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

:::