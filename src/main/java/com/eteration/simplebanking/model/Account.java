package com.eteration.simplebanking.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// This class is a place holder you can change the complete implementation
@Getter
@Setter
@ToString
@AllArgsConstructor
@Entity(name = "account")
public class Account {
    @Id
    @Column(name = "accountNumber")
    private String accountNumber;

    @Column(name = "owner")
    private String owner;

    @Column(name = "balance")
    private double balance;

    @Column(name = "createDate")
    private LocalDateTime createDate;
	
	// @OneToMany(fetch= FetchType.EAGER, cascade=CascadeType.ALL, mappedBy = "transactionId")
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Account() {
    	this.accountNumber = "";
    	this.owner = "";
        balance = 0.0;
        createDate = LocalDateTime.now();
        transactions = new ArrayList<Transaction>();
    }
    
    public Account(String owner, String accountNumber) {
    	this.accountNumber = accountNumber;
    	this.owner = owner;
        balance = 0.0;
        createDate = LocalDateTime.now();
        transactions = new ArrayList<Transaction>();
    }

    public void post(Transaction t) throws InsufficientBalanceException {
        if(t.type == TransactionType.WithdrawalTransaction){
            this.withdraw(t.getAmount());
        } else {
            this.deposit(t.getAmount());
        }
        transactions.add(t);
        
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) throws InsufficientBalanceException {
        if(balance < amount)
            throw new InsufficientBalanceException();
        balance -= amount;
    }

    public void addTransaction(Transaction t) {
        transactions.add(t);
    }
}
