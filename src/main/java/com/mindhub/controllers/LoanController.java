package com.mindhub.controllers;

import com.mindhub.dtos.LoanApplicationDTO;
import com.mindhub.dtos.LoanDTO;
import com.mindhub.models.*;
import com.mindhub.repositories.*;

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
public class LoanController {

    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll()
                .stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @PostMapping("/loans")
    public ResponseEntity<Object> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.findByNumber(loanApplicationDTO.getDestinationAccountNumber());
        Loan loan = loanRepository.getById(loanApplicationDTO.getId());

        if (!loanRepository.findAll().contains(loan) || loanApplicationDTO.getAmount() < 1 || loanApplicationDTO.getAmount() > loan.getMaxAmount() || !loan.getPayments().contains(loanApplicationDTO.getPayments()) || !client.getAccounts().contains(account)){
        return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }

        clientLoanRepository.save(new ClientLoan(loanApplicationDTO.getAmount() + loanApplicationDTO.getAmount() * 0.2, loanApplicationDTO.getPayments(), client, loan));
        transactionRepository.save(new Transaction(account, TransactionType.CREDIT, loanApplicationDTO.getAmount(), "Prestamo " + loan.getName()));

        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }
}
