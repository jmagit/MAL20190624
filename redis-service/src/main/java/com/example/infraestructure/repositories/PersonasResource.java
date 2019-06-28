package com.example.infraestructure.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.domain.entities.Persona;

@RepositoryRestResource(path="personas", itemResourceRel="persona", collectionResourceRel="personas")
public interface PersonasResource extends PagingAndSortingRepository<Persona, String> {

}
