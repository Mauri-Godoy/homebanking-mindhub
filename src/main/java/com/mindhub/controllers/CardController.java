package com.mindhub.controllers;

import com.mindhub.models.*;
import com.mindhub.remove.CardRemove;
import com.mindhub.remove.CardRemoveRepository;
import com.mindhub.repositories.CardRepository;
import com.mindhub.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CardController {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CardRemoveRepository cardRemoveRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> addCard(Authentication authentication, @RequestParam String type, @RequestParam String color) {
        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getCards().size() > 5) {
            return new ResponseEntity<>("403 forbidden", HttpStatus.FORBIDDEN);
        }
        cardRepository.save(new Card(client, CardType.valueOf(type), CardColor.valueOf(color)));
        return new ResponseEntity<>("201 create", HttpStatus.CREATED);}
    
    @PatchMapping("/cards/deletecard")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @RequestParam long id){
        Client client = clientRepository.findByEmail(authentication.getName());

        Card card = cardRepository.getById(id);

        CardRemove cardRemove = new CardRemove(card);

        if (!client.getCards().contains(card)){
            return new ResponseEntity<>("403 forbidden", HttpStatus.FORBIDDEN);
        }
        client.getCards().remove(card);
        client.addCardRemove(cardRemove);
        cardRemoveRepository.save(cardRemove);
        cardRepository.delete(card);
        clientRepository.save(client);
        return new ResponseEntity<>("201 delete", HttpStatus.ACCEPTED);
    }
}
