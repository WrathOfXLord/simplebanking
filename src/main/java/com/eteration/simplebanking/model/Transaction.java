package com.eteration.simplebanking.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


// This class is a place holder you can change the complete implementation
@Getter
@Setter
@Entity(name = "transaction")
@Table(name = "transaction")
@ToString
@NoArgsConstructor
public abstract class Transaction {	
	
    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "amount")
    private double amount;
	
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    protected TransactionType type;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", unique=true, nullable=false)
	private String approvalCode;

    public Transaction(double amount) {
        this.amount = amount;
        date = LocalDateTime.now();
        type = TransactionType.NOT_SET;
    }
}
