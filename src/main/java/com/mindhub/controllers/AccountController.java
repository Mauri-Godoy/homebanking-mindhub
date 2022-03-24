package com.mindhub.controllers;

import com.mindhub.dtos.AccountDTO;
import com.mindhub.models.Account;
import com.mindhub.models.AccountType;
import com.mindhub.models.Client;
import com.mindhub.remove.AccountRemove;
import com.mindhub.remove.AccountRemoveRepository;
import com.mindhub.remove.TransactionRemove;
import com.mindhub.remove.TransactionRemoveRepository;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientRepository;
import com.mindhub.repositories.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AccountController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountRemoveRepository accountRemoveRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionRemoveRepository transactionRemoveRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(AccountDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/accounts/{number}")
    public String getOwnerAccount(@PathVariable String number) {
        return accountRepository.findByNumber(number).getOwner().getFirstName() + " " + accountRepository.findByNumber(number).getOwner().getLastName();
    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> addAccount(Authentication authentication) {
        if (clientRepository.findByEmail(authentication.getName()).getAccounts().size() > 2) {
            return new ResponseEntity<>("403 prohibido", HttpStatus.FORBIDDEN);
        }
        accountRepository.save(new Account(clientRepository.findByEmail(authentication.getName()), LocalDateTime.now(), 0));
        return new ResponseEntity<>("201 creada", HttpStatus.CREATED);
    }

    @PatchMapping("/accounts/deleteaccount")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @RequestParam long id){
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.getById(id);
        AccountRemove accountRemove = new AccountRemove(client, account);

        if (!client.getAccounts().contains(account) || client.getAccounts().size() <= 1){
            return new ResponseEntity<>("No puedes quedarte sin cuentas", HttpStatus.FORBIDDEN);
        }

        client.getAccounts().remove(account);
        accountRepository.delete(account);
        accountRemoveRepository.save(accountRemove);
        account.getTransactions().forEach(transaction -> {
            transactionRemoveRepository.save(new TransactionRemove(accountRemove, transaction));
            transactionRepository.delete(transaction);
        });
        clientRepository.save(client);
        return new ResponseEntity<>("201 delete", HttpStatus.ACCEPTED);
    }

    @PatchMapping("/accounts/addtype")
    public ResponseEntity<Object> addType(Authentication authentication, @RequestParam long id, @RequestParam String type){
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = accountRepository.getById(id);
        if (!client.getAccounts().contains(account) || account.getType() != null){
            return new ResponseEntity<>("403 forbidden", HttpStatus.FORBIDDEN);
        }
        account.setType(AccountType.valueOf(type));
        accountRepository.save(account);
        clientRepository.save(client);
        return new ResponseEntity<>("201 ok", HttpStatus.ACCEPTED);
    }
}

