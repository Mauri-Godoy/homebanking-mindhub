package com.mindhub.models;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	private TransactionType type;
	private double amount;
	private String description;
	private String date;

	@ManyToOne
	@JoinColumn(name = "owner_account_id")
	private Account owner_account;

	public Transaction(Account owner_account, TransactionType type, double amount, String description) {
		this.type = type;
		this.amount = amount;
		this.description = description;
		this.date = formattedDate(LocalDateTime.now());
		this.owner_account = owner_account;
		owner_account.addTransaction(this);
	}

	public String formattedDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(formatter);
	}
}
