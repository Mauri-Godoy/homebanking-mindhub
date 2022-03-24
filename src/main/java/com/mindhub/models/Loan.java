package com.mindhub.models;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private long id;

	private String name;
	private double maxAmount;

	@ElementCollection
	@Column(name = "payment")
	private List<Integer> payments;

	@OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
	private Set<ClientLoan> clientLoans = new HashSet<ClientLoan>();

	public Loan(String name, double maxAmount, List<Integer> payments) {
		this.name = name;
		this.maxAmount = maxAmount;
		this.payments = payments;
	}

	public void addClientLoan(ClientLoan clientLoan) {
		clientLoan.setLoan(this);
		clientLoans.add(clientLoan);
	}

	public List<Client> getClients() {
		return clientLoans.stream().map(ClientLoan::getClient).collect(Collectors.toList());
	}

}
