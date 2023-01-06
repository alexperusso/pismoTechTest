package com.pismo.cartoes.service;

import com.mongodb.DuplicateKeyException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcernResult;
import com.pismo.apicartoes.conta.DocumentoCliente;
import com.pismo.apicartoes.conta.DocumentoConta;
import com.pismo.cartoes.persistence.ContasDocument;
import com.pismo.cartoes.persistence.ContasRepository;
import com.pismo.util.exception.InvalidInputException;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest(classes = CadastroContaService.class)
class CadastroContaServiceTest {

    @MockBean
    private ContasRepository contasRepository;

    @Autowired
    @InjectMocks
    private CadastroContaService cadastroContaService;

    @Test
    void deveCadastrarConta() {
        String cpf_valido = "76080601079";
        DocumentoCliente documentoCliente = new DocumentoCliente(cpf_valido);

        when(contasRepository.save(any()))
                .thenReturn(new ContasDocument(new ObjectId("507f191e810c19729de860ea"), documentoCliente.getDocumentNumber()));

        DocumentoConta documentoConta = cadastroContaService.cadastrar(documentoCliente);

        verify(contasRepository).save(any());
        assertThat(documentoConta.getAccountId()).isNotNull();
        assertThat(documentoConta.getDocumentNumber()).isEqualTo(cpf_valido);
    }

    @Test
    void deveFalharAoCadastrarContaDuplicada() {
        String cpf_valido = "76080601079";
        DocumentoCliente documentoCliente = new DocumentoCliente(cpf_valido);

        //language=JSON
        String docJson = "{\n" +
                "  \"cpf\" :" +
                " \"76080601079\"   \n" +
                "}";
        BsonDocument erroMongo = BsonDocument.parse(docJson);

        when(contasRepository.save(any()))
                .thenThrow(new DuplicateKeyException(erroMongo, new ServerAddress(), WriteConcernResult.unacknowledged()));

        assertThrows(InvalidInputException.class,
                () -> cadastroContaService.cadastrar(documentoCliente));

        verify(contasRepository).save(any());
    }
}