package com.mindhub.remove;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import com.mindhub.models.Transaction;
import com.mindhub.models.TransactionType;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TransactionRemove {

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
	private AccountRemove owner_account;

	public TransactionRemove(AccountRemove owner_account, Transaction transaction) {
		this.type = transaction.getType();
		this.amount = transaction.getAmount();
		this.description = transaction.getDescription();
		this.date = transaction.getDate();
		this.owner_account = owner_account;
		owner_account.addTransaction(this);
	}

	public String formattedDate(LocalDateTime date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return date.format(formatter);
	}
}
