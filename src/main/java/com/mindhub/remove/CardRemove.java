package com.mindhub.remove;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import com.mindhub.models.Card;
import com.mindhub.models.CardColor;
import com.mindhub.models.CardType;
import com.mindhub.models.Client;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CardRemove {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Client owner;

	private String cardHolder;
	private String number;
	private String fromDate;
	private String thruDate;
	private int cvv;
	private CardType type;
	private CardColor color;

	public CardRemove(Card card) {
		this.owner = card.getOwner();
		this.cardHolder = owner.getFirstName() + " " + owner.getLastName();
		this.number = card.getNumber();
		this.fromDate = card.getFromDate();
		this.thruDate = card.getThruDate();
		this.cvv = card.getCvv();
		this.type = card.getType();
		this.color = card.getColor();
		owner.addCardRemove(this);
	}

	public String formattedDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		return date.format(formatter);
	}

}
