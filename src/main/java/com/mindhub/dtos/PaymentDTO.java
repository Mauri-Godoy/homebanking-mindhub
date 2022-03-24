package com.mindhub.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class PaymentDTO {
    String number;
    int cvv;
    double amount;
    String description;

    public PaymentDTO(String number, int cvv, double amount, String description) {
        this.number = number;
        this.cvv = cvv;
        this.amount = amount;
        this.description = description;
    }

}
