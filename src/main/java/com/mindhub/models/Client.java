package com.mindhub.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import com.mindhub.remove.AccountRemove;
import com.mindhub.remove.CardRemove;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private boolean nightMode;

	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Account> accounts = new HashSet<>();

	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<AccountRemove> accountsRemove = new HashSet<>();

	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<Card> cards = new HashSet<>();

	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<CardRemove> cardsRemove = new HashSet<>();

	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
	private Set<ClientLoan> clientLoans = new HashSet<ClientLoan>();

	public Client(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.nightMode = false;
	}

	public Client(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public void addAccount(Account account) {
		account.setOwner(this);
		accounts.add(account);
	}

	public void addAccountRemove(AccountRemove account) {
		account.setOwner(this);
		accountsRemove.add(account);
	}

	public void addCard(Card card) {
		card.setOwner(this);
		cards.add(card);
	}

	public void addClientLoan(ClientLoan clientLoan) {
		clientLoan.setClient(this);
		clientLoans.add(clientLoan);
	}

	public void addCardRemove(CardRemove cardRemove) {
		cardRemove.setOwner(this);
		cardsRemove.add(cardRemove);
	}

	public void switchNightMode() {
		this.setNightMode(!this.isNightMode());
	}
}
