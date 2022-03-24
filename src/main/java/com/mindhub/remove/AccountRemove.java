package com.mindhub.remove;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import com.mindhub.models.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class AccountRemove{

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
    private Set<TransactionRemove> transactions = new HashSet<>();

    public AccountRemove(Client client, Account account) {
        this.number = account.getNumber();
        this.creationDate = account.getCreationDate();
        this.balance = account.getBalance();
        if(account.getType() != null){
            this.type = account.getType();
        }
        this.owner = client;
        owner.addAccountRemove(this);
    }

    public void addTransaction(TransactionRemove transaction){
        transaction.setOwner_account(this);
        if(transaction.getType().equals(TransactionType.DEBIT)) {
            setBalance(getBalance() - transaction.getAmount());
        } else {
            setBalance(getBalance() + transaction.getAmount());
        }

        transactions.add(transaction);
    }
}
