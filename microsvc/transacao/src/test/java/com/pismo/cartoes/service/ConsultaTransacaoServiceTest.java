package com.pismo.cartoes.service;

import com.pismo.apicartoes.transacao.OperationType;
import com.pismo.apicartoes.transacao.TransacaoCartao;
import com.pismo.cartoes.persistence.TransacaoDocument;
import com.pismo.cartoes.persistence.TransacaoRepository;
import com.pismo.cartoes.service.mapper.TransacaoMapper;
import com.pismo.cartoes.service.mapper.TransacaoMapperImpl;
import com.pismo.util.exception.NotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {ConsultaTransacaoService.class, TransacaoMapperImpl.class})
class ConsultaTransacaoServiceTest {


    @MockBean
    private TransacaoRepository transacaoRepository;

    @SpyBean
    private TransacaoMapper transacaoMapper;

    @InjectMocks
    @Autowired
    private ConsultaTransacaoService consultaTransacaoService;

    @Test
    void deveBuscarTransacoesPorConta() {
        String accountId = "507f191e810c19729de860ea";

        TransacaoDocument transacaoDocument = new TransacaoDocument(new ObjectId(accountId),
                1,
                accountId,
                OperationType.PAGAMENTO.getCodOperacao(),
                BigDecimal.TEN,
                LocalDateTime.now());

        when(transacaoRepository.findAllByAccountId(anyString()))
                .thenReturn(List.of(transacaoDocument));


        List<TransacaoCartao> transacoesPorConta = consultaTransacaoService.buscarTransacoesPorConta(accountId);

        verify(transacaoRepository).findAllByAccountId(accountId);
        verify(transacaoMapper).entityToApi(any());
        assertThat(transacoesPorConta).hasSize(1);
    }

    @Test
    void naoDeveEncontrarTransacao() {
        String accountId = "507f191e810c19729de860ea";

        when(transacaoRepository.findAllByAccountId(anyString()))
                .thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class,
                () -> consultaTransacaoService.buscarTransacoesPorConta(accountId));

        verify(transacaoRepository).findAllByAccountId(anyString());
    }
}