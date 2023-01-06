package com.pismo.cartoes.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@TestPropertySource(properties = {
        "spring.mongodb.embedded.version=4.4.16",
        "spring.data.mongodb.host=localhost",
        "spring.data.mongodb.port=27027",
        "spring.data.mongodb.database=test",
        "spring.data.mongodb.uri=mongodb://localhost:27027/test"})
@DataMongoTest
@ExtendWith(SpringExtension.class)
class TransacaoRepositoryIntegrationTest {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Test
    void deveConsultarTransacaoPorDocumentoDeConta() {
        String accountId_valido = "507f1f77bcf86cd799439011";

        TransacaoDocument transacaoDocument = new TransacaoDocument(null,
                null,
                accountId_valido,
                4,
                BigDecimal.TEN,
                LocalDateTime.now());

        transacaoDocument = transacaoRepository.save(transacaoDocument);

        List<TransacaoDocument> transacoes = transacaoRepository.findAllByAccountId(accountId_valido);
        assertThat(transacoes).hasSize(1);
        assertThat(transacoes.get(0).getTransactionId())
                .isNotNull()
                .isEqualTo(transacaoDocument.getTransactionId());
        assertThat(transacoes.get(0).getVersion())
                .isNotNull()
                .isEqualTo(transacaoDocument.getVersion());
    }
}