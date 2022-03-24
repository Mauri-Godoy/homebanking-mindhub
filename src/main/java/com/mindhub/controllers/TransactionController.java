package com.mindhub.controllers;

import com.mindhub.dtos.TransactionDTO;
import com.mindhub.models.*;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/pdftransactions")
    public List<TransactionDTO> getAccounts() {
        return transactionRepository.findAll()
                .stream()
                .map(TransactionDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/transactions")
    public ResponseEntity<Object> addTransaction(Authentication authentication, @RequestParam double amount, @RequestParam String description, @RequestParam String originNumber, @RequestParam String destinationNumber) {
        Account originAccount = accountRepository.findByNumber(originNumber);
        Account destinationAccount = accountRepository.findByNumber(destinationNumber);
        Client client = clientRepository.findByEmail(authentication.getName());
        String originDescription = "Tranferiste a " + destinationAccount.getOwner().getFirstName() + "["+ destinationNumber + "] por " + '"' + description + '"';
        String destinationDescription = "Recibiste de " + originAccount.getOwner().getFirstName() + "[" + originNumber + "] por " + '"' + description + '"';

        if (amount <= 0 || description.isEmpty() || originNumber.isEmpty() || destinationNumber.isEmpty() || originNumber.equals(destinationNumber) || !client.getAccounts().contains(originAccount) || originAccount.getBalance() < amount) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }

        transactionRepository.save(new Transaction(originAccount, TransactionType.DEBIT, amount, originDescription));
        transactionRepository.save(new Transaction(destinationAccount, TransactionType.CREDIT, amount, destinationDescription));

        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }
}
