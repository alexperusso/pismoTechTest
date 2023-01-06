package com.pismo.cartoes.service;

import com.pismo.apicartoes.conta.DocumentoConta;
import com.pismo.cartoes.persistence.ContasDocument;
import com.pismo.cartoes.persistence.ContasRepository;
import com.pismo.util.exception.NotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ConsultarContaService.class)
class ConsultarContaServiceTest {

    @MockBean
    private ContasRepository contasRepository;

    @Autowired
    @InjectMocks
    private ConsultarContaService consultarContaService;

    @Test
    void deveBuscarCadastroConta() {

        String cpf_valido = "76080601079";
        when(contasRepository.findById(anyString()))
                .thenReturn(Optional.of(new ContasDocument(new ObjectId("507f191e810c19729de860ea"), cpf_valido)));

        DocumentoConta documentoConta = consultarContaService.buscar(cpf_valido);

        verify(contasRepository).findById(cpf_valido);

        assertThat(documentoConta)
                .extracting("accountId")
                .isEqualTo("507f191e810c19729de860ea");

        assertThat(documentoConta.getDocumentNumber())
                .isEqualTo("76080601079");
    }

    @Test
    void naoDeveBuscarCadastroConta() {
        String cpf_valido = "76080601079";
        when(contasRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> consultarContaService.buscar(cpf_valido));

        verify(contasRepository).findById(cpf_valido);
    }
}