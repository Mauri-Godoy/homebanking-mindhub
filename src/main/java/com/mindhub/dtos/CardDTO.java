package com.mindhub.dtos;

import com.mindhub.models.Card;
import com.mindhub.models.CardColor;
import com.mindhub.models.CardType;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter 
public class CardDTO {
    private long id;
    private String cardHolder;
    private String number;
    private String fromDate;
    private String thruDate;
    private int cvv;
    private CardType type;
    private CardColor color;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.number = card.getNumber();
        this.fromDate = card.getFromDate();
        this.thruDate = card.getThruDate();
        this.cvv = card.getCvv();
        this.type = card.getType();
        this.color = card.getColor();
    }
}
