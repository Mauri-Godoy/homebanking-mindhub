package com.mindhub.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class LoanApplicationDTO {
    private long id;
    private double amount;
    private Integer payments;
    private String destinationAccountNumber;

    public LoanApplicationDTO(long id, double amount, Integer payments, String destinationAccountNumber) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.destinationAccountNumber = destinationAccountNumber;
    }
}
