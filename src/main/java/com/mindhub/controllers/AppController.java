package com.mindhub.controllers;

import com.mindhub.models.Client;
import com.mindhub.repositories.ClientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
public class AppController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ClientRepository clientRepository;

    @PostMapping("/api/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Complete los datos solicitados", HttpStatus.FORBIDDEN);
        }

        if (clientRepository.findByEmail(email) != null) {
            return new ResponseEntity<>("Nombre de usuario ya registrado", HttpStatus.FORBIDDEN);
        }

        if (password.length() < 8){
            return new ResponseEntity<>("La contraseña no puede tener menos de 8 caracteres", HttpStatus.FORBIDDEN);
        }

        if (!email.contains("@") && !email.contains(".")) {
            return new ResponseEntity<>("Ingrese una dirección de correo valida", HttpStatus.FORBIDDEN);
        }

        for(int i = 0; i < firstName.length(); i++){
            char c = firstName.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return new ResponseEntity<>("El nombre no puede tener números, ni caracteres especiales", HttpStatus.FORBIDDEN);
            }
        }
        for(int i = 0; i < lastName.length(); i++){
            char c = lastName.charAt(i);
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ')) {
                return new ResponseEntity<>("El apellido no puede tener números, ni caracteres especiales", HttpStatus.FORBIDDEN);
            }
        }

        clientRepository.save(new Client(firstName, lastName, email, passwordEncoder.encode(password)));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}