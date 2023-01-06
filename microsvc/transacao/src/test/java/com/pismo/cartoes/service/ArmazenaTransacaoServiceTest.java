package com.pismo.cartoes.service;

import com.mongodb.DuplicateKeyException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcernResult;
import com.pismo.apicartoes.transacao.OperacaoContaCartao;
import com.pismo.apicartoes.transacao.OperationType;
import com.pismo.apicartoes.transacao.TransacaoCartao;
import com.pismo.cartoes.persistence.TransacaoDocument;
import com.pismo.cartoes.persistence.TransacaoRepository;
import com.pismo.cartoes.service.mapper.TransacaoMapper;
import com.pismo.cartoes.service.mapper.TransacaoMapperImpl;
import com.pismo.util.exception.InvalidInputException;
import org.bson.BsonDocument;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ArmazenaTransacaoService.class, TransacaoMapperImpl.class})
class ArmazenaTransacaoServiceTest {

    @MockBean
    private TransacaoRepository transacaoRepository;

    @SpyBean
    private TransacaoMapper transacaoMapper;

    @InjectMocks
    @Autowired
    private ArmazenaTransacaoService armazenaTransacaoService;


    @Test
    void deveArmazenarComoDebitoQuandoOperacoForCompra() {
        String accountId = "507f191e810c19729de860ea";
        OperacaoContaCartao operacaoContaCartao = new OperacaoContaCartao(accountId,
                OperationType.COMPRA_A_VISTA,
                BigDecimal.TEN);

        doAnswer(returnsFirstArg())
                .when(transacaoRepository).save(any(TransacaoDocument.class));

        TransacaoCartao transacaoArmazenada = armazenaTransacaoService.salvar(operacaoContaCartao);

        verify(transacaoRepository).save(any());
        verify(transacaoMapper).entityToApi(any());
        assertThat(transacaoArmazenada.getAmount()).isNegative();
    }

    @Test
    void deveArmazenarComoCreditoQuandoOperacoForPagamento() {
        String accountId = "507f191e810c19729de860ea";
        OperacaoContaCartao operacaoContaCartao = new OperacaoContaCartao(accountId,
                OperationType.PAGAMENTO,
                BigDecimal.TEN);

        doAnswer(returnsFirstArg())
                .when(transacaoRepository).save(any(TransacaoDocument.class));

        TransacaoCartao transacaoArmazenada = armazenaTransacaoService.salvar(operacaoContaCartao);

        verify(transacaoRepository).save(any());
        verify(transacaoMapper).entityToApi(any());
        assertThat(transacaoArmazenada.getAmount()).isPositive();
    }

    @Test
    void deveGerarErroQuandoNaoArmazena() {
        String accountId = "507f191e810c19729de860ea";
        OperacaoContaCartao operacaoContaCartao = new OperacaoContaCartao(accountId,
                OperationType.PAGAMENTO,
                BigDecimal.TEN);

        String docJson = "{\n" +
                "  \"cpf\" :" +
                " \"76080601079\"   \n" +
                "}";
        BsonDocument erroMongo = BsonDocument.parse(docJson);

        doThrow(new DuplicateKeyException(erroMongo, new ServerAddress(), WriteConcernResult.unacknowledged()))
                .when(transacaoRepository).save(any(TransacaoDocument.class));

        assertThrows(InvalidInputException.class,
                () -> armazenaTransacaoService.salvar(operacaoContaCartao));

        verify(transacaoRepository).save(any());
    }
}