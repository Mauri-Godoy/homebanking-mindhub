package com.mindhub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import com.mindhub.functions.functions;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Card {

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

	public Card(Client owner, CardType type, CardColor color) {
		this.owner = owner;
		this.cardHolder = owner.getFirstName() + " " + owner.getLastName();
		this.number = String.valueOf(functions.getRandomNumber(10000000, 99999999))
				+ String.valueOf(functions.getRandomNumber(10000000, 99999999));
		this.fromDate = formattedDate(LocalDateTime.now());
		this.thruDate = formattedDate(LocalDateTime.now().plusYears(5));
		this.cvv = functions.getRandomNumber(100, 999);
		this.type = type;
		this.color = color;
		owner.addCard(this);
	}

	public String formattedDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		return date.format(formatter);
	}
}
