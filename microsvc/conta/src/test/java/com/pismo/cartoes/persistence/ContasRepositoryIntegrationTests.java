package com.pismo.cartoes.persistence;

import com.pismo.cartoes.persistence.ContasDocument;
import com.pismo.cartoes.persistence.ContasRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

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
class ContasRepositoryIntegrationTests {

    @Autowired
    private ContasRepository contasRepository;

    @Test
    public void deveConsultarContaSalvaPorDocumento() {
        String cpf_valido = "59272180082";

        ContasDocument contasDocument = new ContasDocument();
        contasDocument.setDocumentNumber(cpf_valido);

        ContasDocument documentoSalvo = contasRepository.save(contasDocument);

        Optional<ContasDocument> documentoEncontrado = contasRepository
                .findOneByDocumentNumber(cpf_valido);

        assertThat(documentoEncontrado.isPresent()).isTrue();
        assertThat(documentoSalvo.getId()).isEqualTo(documentoEncontrado.get().getId());
    }
}
