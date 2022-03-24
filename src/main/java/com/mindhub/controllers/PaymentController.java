package com.mindhub.controllers;

import com.mindhub.dtos.PaymentDTO;
import com.mindhub.models.*;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.CardRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping("/payments")
    public ResponseEntity<Object> makePayment(@RequestBody PaymentDTO paymentDTO, Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        List<Account> accountList = client.getAccounts().stream().filter(account -> account.getBalance() > paymentDTO.getAmount()).collect(Collectors.toList());
        Card card = cardRepository.findByNumber(paymentDTO.getNumber());
        if(!client.getCards().contains(card)) {
            return new ResponseEntity<>("El número no pertenece a ninguna tarjeta del cliente", HttpStatus.FORBIDDEN);
        }
        if (card.getCvv() != paymentDTO.getCvv()) {
            return new ResponseEntity<>("El codigo de seguridad es incorrecto", HttpStatus.FORBIDDEN);
        }
        if (paymentDTO.getCvv() >= 1000) {
            return new ResponseEntity<>("El codigo de seguridad no puede tener mas de 3 dígitos", HttpStatus.FORBIDDEN);
        }
        if (accountList.size() < 1)  {
            return new ResponseEntity<>("No tienes el balance suficiente para llevar a cabo la operación", HttpStatus.FORBIDDEN);
        }

        String description = "Realizaste un pago por \"" + paymentDTO.getDescription() + '"';
        Account account = accountList.get(0);
        transactionRepository.save(new Transaction(account,TransactionType.DEBIT, paymentDTO.getAmount(), description));
        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }
}
