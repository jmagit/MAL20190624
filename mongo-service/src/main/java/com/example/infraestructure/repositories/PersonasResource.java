package com.example.infraestructure.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domain.entities.Persona;

@RepositoryRestResource(path="personas", itemResourceRel="persona", collectionResourceRel="personas")
public interface PersonasResource extends MongoRepository<Persona, String> {

}
