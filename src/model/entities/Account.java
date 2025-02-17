package model.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Account {
    private UUID id;
    private String accountNumber;
    private String accountAgency;
    private Client client;
    private Double balance;
    private Double bound;
    private String accountType;
    private List<Transaction> transactions;
    private Boolean transferBlocked;
    private Double transferLimit;
    private Double transferAccumulated;

    public Account(String accountNumber, String accountAgency, Client client, Double balance, Double bound, String accountType, Boolean transferBlocked, Double transferLimit, Double transferAccumulated ){
        super();
        this.accountNumber = accountNumber;
        this.accountAgency = accountAgency;
        this.client = client;
        this.balance = balance;
        this.bound = bound;
        this.accountType = accountType;
        this.transferBlocked = transferBlocked;
        this.transferLimit = transferLimit;
        this.transferAccumulated = transferAccumulated;
    }


    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getAccountNumber(){
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public String getAccountAgency(){
        return accountAgency;
    }

    public void setAccountAgency(String accountAgency){
        this.accountAgency = accountAgency;
    }

    public Client getClient(){
        return client;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public Double getBalance(){
        return balance;
    }

    public void setBalance(Double balance){
        this.balance = balance;
    }

    public Double getBound(){
        return bound;
    }

    public void setBound(Double bound){
        this.bound = bound;
    }

    public String getAccountType(){
        return accountType;
    }

    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions){
        this.transactions = transactions;
    }



    public boolean isTransferBlocked() {
        return transferBlocked;
    }

    public void setTransferBlocked(boolean transferBlocked) {
        this.transferBlocked = transferBlocked;
    }

    public Double getTransferLimit() {
        return transferLimit;
    }

    public void setTransferLimit(Double transferLimit) {
        this.transferLimit = transferLimit;
    }

    public Double getTransferAccumulated() {
        return transferAccumulated;
    }

    public void setTransferAccumulated(Double transferAccumulated) {
        this.transferAccumulated = transferAccumulated;
    }

    public void resetTransferAccumulated() {
        this.transferAccumulated = 0.0;
    }


    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountNumber='" + accountNumber + '\'' +
                ", accountAgency='" + accountAgency + '\'' +
                ", client=" + client +
                ", balance=" + balance +
                ", bound=" + bound +
                ", accountType='" + accountType + '\'' +
                ", transactions=" + transactions +
                ", transferLimit=" + transferLimit +
                '}';
    }
}
