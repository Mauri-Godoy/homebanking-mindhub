package com.mindhub.remove;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRemoveRepository extends JpaRepository<CardRemove, Long> {
}
