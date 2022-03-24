package com.mindhub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import com.mindhub.functions.functions;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;
	private String number;
	private LocalDateTime creationDate;
	private double balance;
	private AccountType type;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	private Client owner;

	@OneToMany(mappedBy = "owner_account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Transaction> transactions = new HashSet<>();

	public Account(Client owner, LocalDateTime creationDate, double balance) {
		this.creationDate = creationDate;
		this.balance = balance;
		this.owner = owner;
		owner.addAccount(this);
		this.number = "VIN-" + owner.getId() + functions.getRandomNumber(1, 9999999);
	}

	public void addTransaction(Transaction transaction) {
		transaction.setOwner_account(this);
		if (transaction.getType().equals(TransactionType.DEBIT)) {
			setBalance(getBalance() - transaction.getAmount());
		} else {
			setBalance(getBalance() + transaction.getAmount());
		}

		transactions.add(transaction);
	}
}
