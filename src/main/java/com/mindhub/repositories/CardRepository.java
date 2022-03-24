package com.mindhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.mindhub.models.Card;

@RepositoryRestResource
public interface CardRepository  extends JpaRepository<Card, Long> {
    Card findByNumber(String number);
}
