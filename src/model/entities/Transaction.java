package model.entities;

import java.util.Date;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private Date date;
    private String transactionType;
    private Double value;
    private Account sourceAccount;
    private Account destinationAccount;

    public Transaction(Date date, String transactionType, Double value, Account sourceAccount, Account destinationAccount){
        super();
        this.date = date;
        this.transactionType = transactionType;
        this.value = value;
        this.sourceAccount =sourceAccount;
        this.destinationAccount = destinationAccount;

    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public String getTransactionType(){
        return transactionType;
    }

    public void setTransactionType(String transactionType){
        this.transactionType = transactionType;
    }

    public Double getValue(){
        return value;
    }

    public void setValue(Double value){
        this.value = value;
    }

    public Account getSourceAccount(){
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount){
        this.sourceAccount = sourceAccount;
    }

    public Account getDestinationAccount(){
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount){
        this.destinationAccount = destinationAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", date=" + date +
                ", transactionType='" + transactionType + '\'' +
                ", value=" + value +
                ", sourceAccount=" + (sourceAccount != null ? sourceAccount.getId() : "null") +
                ", destinationAccount=" + (destinationAccount != null ? destinationAccount.getId() : "null") +
                '}';
    }
}