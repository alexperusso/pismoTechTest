package com.pismo.cartoes.service;

import co.elastic.apm.api.CaptureSpan;
import co.elastic.apm.api.CaptureTransaction;
import com.pismo.apicartoes.transacao.OperacaoContaCartao;
import com.pismo.apicartoes.transacao.OperationType;
import com.pismo.apicartoes.transacao.TransacaoCartao;
import com.pismo.cartoes.persistence.TransacaoDocument;
import com.pismo.cartoes.persistence.TransacaoRepository;
import com.pismo.cartoes.service.mapper.TransacaoMapper;
import com.pismo.util.exception.InvalidInputException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.annotation.ContinueSpan;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.cloud.sleuth.annotation.SpanTag;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class ArmazenaTransacaoService {

    private TransacaoRepository transacaoRepository;
    private TransacaoMapper transacaoMapper;

    @NewSpan(name = "armazenaTransacao")
    @CaptureSpan(value="salvar") //Kibana APM Server
    public TransacaoCartao salvar(OperacaoContaCartao operacaoContaCartao) {
        log.trace("Armazenando nova transacao");

        TransacaoDocument transacaoDocument = this.criarTransacao(operacaoContaCartao);

        transacaoDocument = armazenarTransacao(operacaoContaCartao, transacaoDocument);

        log.debug("Transacao armazenada");
        return transacaoMapper.entityToApi(transacaoDocument);
    }

    @CaptureTransaction(type = "Task", value = "Armazenamento")
    private TransacaoDocument armazenarTransacao(OperacaoContaCartao operacaoContaCartao, TransacaoDocument transacaoDocument) {
        try {
            transacaoDocument = transacaoRepository.save(transacaoDocument);
        } catch (Exception excp) {
            log.debug("Erro ao armazenar transacao em conta do cliente {}", operacaoContaCartao);
            log.error("Erro ao armazenar transacao", excp);
            throw new InvalidInputException(excp.getMessage());
        }
        return transacaoDocument;
    }

    @ContinueSpan(log = "traduzTransacao")
    private TransacaoDocument criarTransacao(@SpanTag("transacao") final OperacaoContaCartao operacaoContaCartao) {
        log.trace("Criando Documento de transacao");

        TransacaoDocument transacaoDocument = new TransacaoDocument();

        transacaoDocument.setAccountId(operacaoContaCartao.getAccountId());
        transacaoDocument.setEventDate(LocalDateTime.now());
        transacaoDocument.setOperationTypeId(operacaoContaCartao.getOperationTypeId().getCodOperacao());

        if (OperationType.PAGAMENTO.equals(operacaoContaCartao.getOperationTypeId())) {
            transacaoDocument.setAmount(operacaoContaCartao.getAmount());
        } else {
            transacaoDocument.setAmount(operacaoContaCartao.getAmount().negate());
        }

        log.debug("Documento de transacao criado");
        return transacaoDocument;
    }

}
