package com.mindhub.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import com.mindhub.models.Loan;

@Getter @Setter 
public class LoanDTO {

    private long id;
    private String name;
    private double maxAmount;
    private List<Integer> payments;

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
    }

}
