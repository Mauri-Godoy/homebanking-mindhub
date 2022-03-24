package com.mindhub.dtos;

import com.mindhub.models.ClientLoan;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class ClientLoanDTO{
    long id;
    long loanId;
    String name;
    double amount;
    Integer payments;

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
    }
}
