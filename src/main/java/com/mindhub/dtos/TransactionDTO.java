package com.mindhub.dtos;

import com.mindhub.models.Transaction;
import com.mindhub.models.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class TransactionDTO {
    private long id;
    private TransactionType type;
    private double amount;
    private String description;
    private String date;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
    }

}
