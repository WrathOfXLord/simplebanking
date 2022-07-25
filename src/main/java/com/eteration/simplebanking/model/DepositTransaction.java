package com.eteration.simplebanking.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

// This class is a place holder you can change the complete implementation
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
public class DepositTransaction extends Transaction  {
    
    @JsonCreator
    public DepositTransaction(@JsonProperty(value = "transactionId", required = false) Long transactionId, 
                              @JsonProperty(value = "amount", required = true) double amount, 
                              @JsonProperty(value = "date", required = false) LocalDateTime date, 
                              @JsonProperty(value = "type", required = false) TransactionType type) {
        super(amount);
        this.type = TransactionType.DepositTransaction;
    }

    public DepositTransaction(double amount) {
        super(amount);
        this.type = TransactionType.DepositTransaction;
    }

    public DepositTransaction() {
        super();
        this.type = TransactionType.DepositTransaction;
    }
    
}
