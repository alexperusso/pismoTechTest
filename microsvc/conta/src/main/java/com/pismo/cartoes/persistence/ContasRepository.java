package com.pismo.cartoes.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ContasRepository extends MongoRepository<ContasDocument, String> {

    Optional<ContasDocument> findById(String id);

    Optional<ContasDocument> findOneByDocumentNumber(String documentNumber);
}
