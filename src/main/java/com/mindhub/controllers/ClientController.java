package com.mindhub.controllers;

import com.mindhub.dtos.ClientDTO;
import com.mindhub.models.Client;
import com.mindhub.repositories.AccountRepository;
import com.mindhub.repositories.ClientLoanRepository;
import com.mindhub.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ClientController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return clientRepository.findAll()
                .stream()
                .map(ClientDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentclient(Authentication authentication) {
        return new ClientDTO(clientRepository.findByEmail(authentication.getName()));
    }

    @PatchMapping("/nightmode")
    public ResponseEntity<Object> activeNightMode(Authentication authentication) {
        Client client = clientRepository.findByEmail(authentication.getName());
        client.switchNightMode();
        clientRepository.save(client);
        return new ResponseEntity<>("201", HttpStatus.OK);
    }
}

