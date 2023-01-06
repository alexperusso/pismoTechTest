package com.pismo.cartoes.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TransacaoRepository extends MongoRepository<TransacaoDocument, String> {

    Optional<TransacaoDocument> findById(String id);

    List<TransacaoDocument> findAllByAccountId(String accountId);
}
