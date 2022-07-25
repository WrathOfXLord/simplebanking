package com.eteration.simplebanking.controller;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.eteration.simplebanking.model.Transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// This class is a place holder you can change the complete implementation
@Getter
@Setter
@Entity(name = "transaction_status")
@Table(name = "transaction_status")
@ToString
@NoArgsConstructor
public class TransactionStatus implements Serializable {
    @Column(name = "status")
    private String status;
    
    @Id
    @Column(name = "id")
    private String approvalCode;

    public TransactionStatus(Transaction transaction) {
        this.status = "OK";
        // System.out.println("Code: " + transaction.getApprovalCode());
        this.approvalCode = transaction.getApprovalCode();
    }
}
